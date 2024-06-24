<template>
    <div id="postaviProizvod">
      <img src="images/ShopLogo.jpg" alt="Logo" class="logo" />
      <h1>Postavi Novi Proizvod</h1>
      <form @submit.prevent="postaviProizvod">
        <div>
          <label for="naziv">Naziv:</label>
          <input type="text" id="naziv" v-model="naziv" required />
        </div>
        <div>
          <label for="opis">Opis:</label>
          <textarea id="opis" v-model="opis" required></textarea>
        </div>
        <div>
          <label for="cena">Cena:</label>
          <input type="number" id="cena" v-model.number="cena" required />
        </div>
        <div>
          <label for="kategorija">Kategorija:</label>
          <select id="kategorija" v-model="kategorijaNaziv" required>
            <option v-for="kategorija in kategorije" :key="kategorija.id" :value="kategorija">{{ kategorija.naziv }}</option>
          </select>
          <input type="text" v-model="novaKategorijaNaziv" placeholder="Dodaj novu kategoriju" />
        </div>
        <div>
          <label for="tipProdaje">Tip Prodaje:</label>
          <select id="tipProdaje" v-model="tipProdaje" required>
            <option value="AUKCIJA">Aukcija</option>
            <option value="FIKSNACENA">Fiksna Cena</option>
          </select>
        </div>
        <div>
          <label for="slika">Slika:</label>
          <input type="file" id="slika" @change="handleFileUpload" required />
        </div>
        <button type="submit">Postavi Proizvod</button>
        <button @click="nazad">Nazad</button>
      </form>
    </div>
</template>
  
<script>
import axios from "axios";
  
export default {
    data() {
        return {
            naziv: "",
            opis: "",
            cena: null,
            kategorija: null,
            novaKategorijaNaziv: "",
            tipProdaje: "AUKCIJA",
            slika: null,
            kategorije: []
        };
    },
    mounted() {
        this.ucitajSveKategorije();
    },
    methods: {
        ucitajSveKategorije() {
            axios.get("http://localhost:8081/kategorije", { withCredentials: true })
                .then((res) => {
                    this.kategorije = res.data;
                })
                .catch((err) => {
                    console.error("Sve kategorije Error:", err);
                });
        },
        handleFileUpload(event) {
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
                this.slika = reader.result;
            };
        },
        postaviProizvod() {
            let kategorija = this.kategorija;
            if (this.novaKategorijaNaziv.trim() !== "") {
                kategorija = { naziv: this.novaKategorijaNaziv.trim() };
            }
  
            const proizvod = {
                naziv: this.naziv,
                opis: this.opis,
                cena: this.cena,
                kategorija: this.kategorije.find(kat => kat.naziv === (this.novaKategorija || this.kategorijaNaziv)),
                tipProdaje: this.tipProdaje,
                slika: this.slika
            };

            console.log("Slanje podataka o proizvodu:", JSON.stringify(proizvod, null, 2));
  
            axios.post("http://localhost:8081/api/postavi-proizvod", proizvod, { withCredentials: true })
                .then((res) => {
                    alert("Proizvod je uspešno postavljen!");
                    this.$router.push("/homeProdavac");
                })
                .catch((err) => {
                    console.error("Error:", err);
                    alert("Greška prilikom postavljanja proizvoda.");
                });
        },
        nazad() {
            this.$router.push("/homeProdavac");
        }
    }
};
</script>
  
<style>
.logo {
    display: block;
    margin: 0 auto;
    max-width: 200px;
    max-height: 100px;
    width: auto;
    height: auto;
}
#postaviProizvod {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
h1 {
    text-align: center;
    margin-bottom: 20px;
}
form div {
    margin-bottom: 15px;
}
label {
    display: block;
    margin-bottom: 5px;
}
input, textarea, select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 4px;
    margin-right: 10px;
}
button:hover {
    background-color: #0056b3;
}
</style>
