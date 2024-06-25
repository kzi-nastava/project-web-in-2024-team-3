<template>
    <div id="ponude" class="container mt-5">
      <h1 class="text-center">Ponude za proizvod</h1>
      <div v-if="ponude.length">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Korisničko ime kupca</th>
              <th>Cena ponude</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="ponuda in ponude" :key="ponuda.id">
              <td>{{ ponuda.korisnickoIme }}</td>
              <td>{{ ponuda.cena }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        <p class="text-center">Nema ponuda za ovaj proizvod.</p>
      </div>
      <button @click="nazad" class="btn btn-secondary mt-3">Nazad</button>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "Ponude",
    data() {
      return {
        ponude: []
      };
    },
    mounted() {
      this.ucitajPonude();
    },
    methods: {
      ucitajPonude() {
        const id = this.$route.query.id;
        axios
          .get(`http://localhost:8081/proizvodi/${id}/ponude`, { withCredentials: true })
          .then((res) => {
            console.log("Ponude Response:", res.data);
            this.ponude = res.data;
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      },
      nazad() {
        this.$router.go(-1);
      }
    }
  };
  </script>
  
  <style scoped>
  #ponude {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  #ponude h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  .table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
  }
  .table th,
  .table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
  }
  .table th {
    background-color: #f2f2f2;
  }
  .table tr:nth-child(even) {
    background-color: #f2f2f2;
  }
  .table tr:hover {
    background-color: #ddd;
  }
  </style>
  