<template>
    <div class="container mt-5">
      <div class="card p-3 mb-4">
        <h1 class="text-center">Oceni Kupca</h1>
        <div v-if="kupac">
          <p><strong>Ime kupca:</strong> {{ kupac.ime }} {{ kupac.prezime }}</p>
          <form @submit.prevent="posaljiOcenu">
            <div class="form-group">
              <label for="ocena">Ocena (1-5):</label>
              <select v-model="ocena" id="ocena" class="form-control">
                <option v-for="i in 5" :key="i" :value="i">{{ i }}</option>
              </select>
            </div>
            <div class="form-group">
              <label for="komentar">Komentar:</label>
              <textarea v-model="komentar" id="komentar" class="form-control" rows="3"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Pošalji ocenu</button>
          </form>
        </div>
        <button @click="nazad" class="btn btn-secondary mt-3">Nazad</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "OceniKupca",
    data() {
      return {
        kupac: null,
        ocena: null,
        komentar: "",
      };
    },
    mounted() {
      this.ucitajKupca();
    },
    methods: {
      ucitajKupca() {
        const id = this.$route.query.id;
        axios
          .get(`http://localhost:8081/kupac/${id}`, { withCredentials: true })
          .then((res) => {
            this.kupac = res.data;
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      },
      posaljiOcenu() {
        const recenzijaDto = {
          ocena: this.ocena,
          komentar: this.komentar,
        };
        const id = this.$route.query.id;
        axios
          .post(`http://localhost:8081/api/oceni-kupca/${id}`, recenzijaDto, { withCredentials: true })
          .then(() => {
            alert("Ocena je uspešno poslata!");
            this.$router.push({ path: '/kupacProfil', query: { id } });
          })
          .catch((err) => {
            console.error("Error:", err);
            alert("Došlo je do greške prilikom slanja ocene.");
          });
      },
      nazad() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 8px;
  }
  .card {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
  