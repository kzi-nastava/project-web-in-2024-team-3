<template>
  <div id="kupacProizvodi">
    <img src="images/ShopLogo.jpg" alt="Logo" class="logo"/>
    <br /><br />
    <div class="kontejner-proizvodi">
      <!-- Pretraga po nazivu ili opisu -->
      <input class="input" type="text" v-model="pretraga" placeholder="Pretraži proizvode po nazivu ili opisu" />
      <button @click="pretrazi">Pretraži</button>
      
      <!-- Filtriranje -->
      <div class="filtriranje">
        <label class="od">Cena od:</label>
        <input type="number" v-model.number="cenaOd" />

        <label class="do">do:</label>
        <input type="number" v-model.number="cenaDo" />
        
        <label class="tip">Tip prodaje:</label>
        <select v-model="tipProdaje">
          <option value="">Svi tipovi</option>
          <option value="FIKSNA_CENA">Fiksna cena</option>
          <option value="AUKCIJA">Aukcija</option>
        </select>
        
        <!-- Dropdown za kategorije -->
        <label class="kategorija">Kategorija:</label>
        <select v-model="kategorijaNaziv">
          <option value="">Sve kategorije</option>
          <option v-for="kategorija in filtriraneKategorije" :key="kategorija.id" :value="kategorija.naziv">{{ kategorija.naziv }}</option>
        </select>
        <button @click="filtrirajProizvode">Filtriraj</button>
      </div>
      
      <table id="proizvodi">
        <tr>
          <th>Naziv</th>
          <th>Cena</th>
          <th>Vise</th>
          <th>Akcije</th>
        </tr>
        <tr v-for="proizvod in paginiraniProizvodi" :key="proizvod.id">
          <td>{{ proizvod.naziv }}</td>
          <td>{{ proizvod.cena }}</td>
          <td>
            <button class="btnSeeMore" @click="seeMore(proizvod)">
              Prikazi više
            </button>
          </td>
          <td>
            <button v-if="proizvod.tipProdaje === 'FIKSNACENA'" @click="kupiProizvod(proizvod)">
              Kupi proizvod
            </button>
            <div v-if="proizvod.tipProdaje === 'AUKCIJA'">
              <input
                type="number"
                v-model.number="ponudaCene[proizvod.id]"
                placeholder="Unesite ponudu"
              />
              <button @click="napraviPonudu(proizvod.id)">Napravi ponudu</button>
              <button @click="prikaziPonude(proizvod.id)">Vidi ponude</button>
            </div>
          </td>
        </tr>
      </table>
    </div>
    <br />
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">Prethodna</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Sledeća</button>
    </div>
    <br />
    <div class="container-buttons">
      <button @click="azurirajProfil">Ažuriraj profil</button>
      <button @click="pregledKorisnika">Pregledaj korisnike</button>
      <button @click="logout">Izloguj se</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import PregledKorisnika from "./PregledKorisnika.vue";

export default {
  name: "KupacHomeView",
  data() {
    return {
      proizvodi: [],
      filtriraniProizvodi: [],
      paginiraniProizvodi: [],
      pretraga: "",
      cenaOd: null,
      cenaDo: null,
      tipProdaje: "",
      kategorijaNaziv: "", 
      kategorije: [], 
      filtriraneKategorije: [],
      currentPage: 1,
      itemsPerPage: 5,
      totalPages: 1,
      ponudaCene: {}
    };
  },
  mounted() {
    this.ucitajProizvode();
    this.ucitajSveKategorije(); 
  },
  methods: {
    ucitajProizvode() {
      axios
        .get("http://localhost:8081/api/proizvodi", { withCredentials: true })
        .then((res) => {
          console.log("Response:", res.data);
          this.proizvodi = res.data;
          this.filtriraniProizvodi = res.data;
          this.updatePagination();
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    ucitajSveKategorije() {
      axios
        .get("http://localhost:8081/kategorije", { withCredentials: true })
        .then((res) => {
          console.log("Sve kategorije Response:", res.data);
          this.kategorije = res.data;
          this.filtriraneKategorije = res.data; 
        })
        .catch((err) => {
          console.error("Sve kategorije Error:", err);
        });
    },
    seeMore(proizvod) {
      this.$router.push({ path: '/proizvod', query: { id: proizvod.id, role: 'kupac' } });
    },
    kupiProizvod(proizvod) {
      axios
        .post(`http://localhost:8081/api/proizvod-fiskna/${proizvod.id}`, {}, { withCredentials: true })
        .then((res) => {
          this.successMessage = "Proizvod uspešno kupljen!";
          this.errorMessage = "";
          
          this.filtriraniProizvodi = this.filtriraniProizvodi.filter(p => p.id !== proizvod.id);
          this.updatePagination();
        })
        .catch((err) => {
          this.errorMessage = "Greška pri kupovini proizvoda: " + err.response.data;
          this.successMessage = "";
          console.error("Error:", err);
        });
    },
    napraviPonudu(proizvodId) {
      const cena = this.ponudaCene[proizvodId];
      if (cena) {
        axios
          .post(`http://localhost:8081/api/proizvod-aukcija/${proizvodId}`, null, {
            params: { ponuda: parseFloat(cena) },
            withCredentials: true
          })
          .then((res) => {
            this.successMessage = "Ponuda uspešno poslata!";
            this.errorMessage = "";
            this.ponudaCene[proizvodId] = ""; 
            this.ucitajProizvode(); 
          })
          .catch((err) => {
            if (err.response && err.response.status === 400) {
              this.errorMessage = "Greška pri slanju ponude: Niste prijavljeni ili ponuda nije validna.";
            } else if (err.response && err.response.status === 500) {
              this.errorMessage = "Već imate najveću ponudu.";
            } else {
              this.errorMessage = "Greška pri slanju ponude: " + err.response.data;
            }
            this.successMessage = "";
            console.error("Error:", err);
          });
      } else {
        this.errorMessage = "Morate uneti cenu za ponudu.";
        this.successMessage = "";
      }
    },
    prikaziPonude(proizvodId) {
      this.$router.push({ path: '/ponude', query: { id: proizvodId } });
    },
    logout() {
      axios
      .post("http://localhost:8081/api/logout", {}, { withCredentials: true })
      .then(() => {
      this.$router.push("/");
      })
      .catch((err) => {
        console.error("Logout Error:", err);
        if (err.response && err.response.status === 403) {
          alert("Niste ulogovani ili nemate privilegije za ovu akciju.");
        } else {
          alert("Došlo je do greške prilikom pokušaja logout-a.");
        }
      });
    },
    pretrazi() {
      if (this.pretraga.trim() !== "") {
        let pretragaLower = this.pretraga.toLowerCase().trim();
        this.filtriraniProizvodi = this.proizvodi.filter((proizvod) =>
          proizvod.naziv.toLowerCase().includes(pretragaLower) ||
          proizvod.opis.toLowerCase().includes(pretragaLower)
        );
      } else {
        this.filtriraniProizvodi = [...this.proizvodi]
      }
      this.updatePagination();
    },
    filtrirajProizvode() {
      let params = {};
      if (this.cenaOd !== null) {
        params.min = this.cenaOd;
      }
      if (this.cenaDo !== null) {
        params.max = this.cenaDo;
      }
      if (this.tipProdaje !== "") {
        params.tipProdaje = this.tipProdaje; 
      }
      if (this.kategorijaNaziv !== "") {
        params.kategorija = this.kategorijaNaziv; 
      }

      console.log("Parametri za filtriranje:", params);

      axios
        .get(`http://localhost:8081/api/proizvodi/filtrirajProizvode`, {
          params: params,
          withCredentials: true,
        })
        .then((res) => {
          console.log("Filtriranje Response:", res.data);
          this.filtriraniProizvodi = res.data;
          this.updatePagination();
          if (res.data.length === 0) {
            console.warn("Server je vratio prazan odgovor.");
          }
        })
        .catch((err) => {
          console.error("Filtriranje Error:", err);
        });
    },
    azurirajProfil() {
      this.$router.push({ path: '/azurirajProfil', query: { role: 'kupac' } });

    },
    pregledKorisnika() {
      this.$router.push({ path: '/pregledKorisnika', query: { role: 'kupac' } });
    },
    oceniProdavca() {
      this.$router.push("/oceniProdavca");
    },
    prijaviProdavca() {
      this.$router.push("/prijaviProdavca");
    },
    updatePagination() {
      this.totalPages = Math.ceil(this.filtriraniProizvodi.length / this.itemsPerPage);
      this.currentPage = Math.min(this.currentPage, this.totalPages);
      this.paginiraniProizvodi = this.filtriraniProizvodi.slice(
        (this.currentPage - 1) * this.itemsPerPage,
        this.currentPage * this.itemsPerPage
      );
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.updatePagination();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.updatePagination();
      }
    },
  },
  watch: {
    currentPage() {
      this.updatePagination();
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
.od {
  padding-left: auto;
}
.do {
  margin-left: 60px;
}
.kategorija {
  margin-left: 60px;
}
.tip {
  margin-left: 60px;
}
.input {
  width: 220px;
  height: 20px;
  margin-bottom: 10px;
}
h1 {
  color: #00BFFF;
}
.kontejner-proizvodi {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.filtriranje {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  margin-top: 20px;
}
.filtriranje label {
  margin-right: 10px;
}
.filtriranje input,
.filtriranje select,
.filtriranje button {
  margin-left: 10px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.filtriranje button {
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
.filtriranje button:hover {
  background-color: #0056b3;
}
#proizvodi {
  width: 80%;
  margin-top: 20px;
  border-collapse: collapse;
}
#proizvodi th,
#proizvodi td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
#proizvodi th {
  background-color: #f2f2f2;
}
#proizvodi tr:nth-child(even) {
  background-color: #f2f2f2;
}
#proizvodi tr:hover {
  background-color: #ddd;
}
.container-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
button {
  margin-left: 10px;
  padding: 10px 20px;
  background-color: #fdfeff;
  color: #080000;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}
button:hover {
  background-color: #c0c0c0;
}
body {
  background-color: #dcdcdc;
  color: white;
  font-family: Arial, sans-serif;
}
</style>
