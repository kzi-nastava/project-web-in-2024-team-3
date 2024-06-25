<template>
    <div id="prijaviKupca" class="container mt-5">
      <h1 class="text-center">Prijavi Kupca</h1>
      <form @submit.prevent="submitPrijava">
        <div class="form-group">
          <label for="razlog">Razlog prijave:</label>
          <textarea id="razlog" v-model="razlog" class="form-control" rows="5" required></textarea>
        </div>
        <button type="submit" class="btn btn-danger mt-3">Podnesi prijavu</button>
      </form>
      <button @click="nazad" class="btn btn-secondary mt-3">Nazad</button>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "PrijaviKupca",
    data() {
      return {
        razlog: "",
        ulogovanKorisnik: null,
      };
    },
    mounted() {
      this.ucitajUlogovanogKorisnika();
    },
    methods: {
      ucitajUlogovanogKorisnika() {
        this.ulogovanKorisnik = JSON.parse(localStorage.getItem('ulogovanKorisnik'));
      },
      submitPrijava() {
        const id = this.$route.query.id;
        const prijava = { razlogPrijave: this.razlog };
  
        axios
          .post(`http://localhost:8081/api/prijavi-kupca/${id}`, prijava, { withCredentials: true })
          .then((res) => {
            alert("Prijava uspešno podneta!");
            this.$router.push({ path: '/pregledKorisnika', query: { role: 'prodavac' } });
          })
          .catch((err) => {
            console.error("Error:", err);
            alert("Greška prilikom podnošenja prijave.");
          });
      },
      nazad() {
        this.$router.go(-1);
      }
    }
  };
  </script>
  
  <style scoped>
  #prijaviKupca {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  </style>
  