<template>
  <div id="azuriranjeProizvoda" class="container mt-5">
    <h1 class="text-center">Ažuriranje Proizvoda</h1>
    <div v-if="proizvod">
      <form @submit.prevent="submitAzuriranje">
        <div class="form-group">
          <label for="naziv">Naziv</label>
          <input id="naziv" v-model="proizvod.naziv" class="form-control" />
        </div>
        <div class="form-group">
          <label for="opis">Opis</label>
          <textarea id="opis" v-model="proizvod.opis" class="form-control"></textarea>
        </div>
        <div class="form-group">
          <label for="cena">Cena</label>
          <input id="cena" v-model.number="proizvod.cena" type="number" class="form-control" />
        </div>
        <div class="form-group">
          <label for="kategorija">Kategorija</label>
          <select id="kategorija" v-model="proizvod.kategorija" class="form-control">
            <option v-for="kategorija in kategorije" :key="kategorija.id" :value="kategorija.naziv">
              {{ kategorija.naziv }}
            </option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Sačuvaj izmene</button>
      </form>
    </div>
    <button @click="nazad" class="btn btn-secondary mt-3">Nazad</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AzuriranjeProizvoda",
  data() {
    return {
      proizvod: null,
      kategorije: []
    };
  },
  mounted() {
    this.ucitajProizvod();
    this.ucitajSveKategorije();
  },
  methods: {
    ucitajProizvod() {
      const id = this.$route.query.id;
      axios
        .get(`http://localhost:8081/api/proizvodi/${id}`, { withCredentials: true })
        .then((res) => {
          this.proizvod = res.data;
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    ucitajSveKategorije() {
      axios
        .get("http://localhost:8081/kategorije", { withCredentials: true })
        .then((res) => {
          this.kategorije = res.data;
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    submitAzuriranje() {
      if (this.proizvod.tipProdaje === 'AUKCIJA') {
        alert("Nije moguće ažurirati proizvod koji ima ponude.");
        return;
      }

      const id = this.$route.query.id;
      axios
        .put(`http://localhost:8081/api/azuriranjeProizvoda/${id}`, this.proizvod, { withCredentials: true })
        .then((res) => {
          alert("Proizvod uspešno ažuriran!");
          this.$router.push({ path: '/homeProdavac' });
        })
        .catch((err) => {
          console.error("Error:", err);
          alert("Greška pri ažuriranju proizvoda: " + err.response.data);
        });
    },
    nazad() {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
h1 {
  text-align: center;
  margin-bottom: 20px;
}
.form-group {
  margin-bottom: 15px;
}
.btn-primary {
  background-color: #007bff;
  color: white;
}
.btn-secondary {
  background-color: #6c757d;
  color: white;
}
</style>
