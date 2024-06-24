<template>
    <div id="pregledKorisnika">
      <h1>Pregled Korisnika</h1>
      <table>
        <thead>
          <tr>
            <th>Ime</th>
            <th>Prezime</th>
            <th>Uloga</th>
            <th>Akcija</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="korisnik in korisnici" :key="korisnik.id">
            <td>{{ korisnik.ime }}</td>
            <td>{{ korisnik.prezime }}</td>
            <td>{{ korisnik.uloga }}</td>
            <td>
              <button class="button" @click="idiNaProfil(korisnik)">Idi na profil</button>
            </td>
          </tr>
        </tbody>
      </table>
      <button @click="nazad">Nazad</button>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "PregledKorisnika",
    data() {
      return {
        korisnici: []
      };
    },
    mounted() {
      this.ucitajKorisnike();
    },
    methods: {
      ucitajKorisnike() {
        axios
          .get("http://localhost:8081/sviProfili", { withCredentials: true })
          .then((res) => {
            this.korisnici = res.data;
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      },
      idiNaProfil(korisnik) {
        if (korisnik.uloga === 'KUPAC') {
          this.$router.push({ path: '/kupacProfil', query: { id: korisnik.id } });
        } else if (korisnik.uloga === 'PRODAVAC') {
          this.$router.push({ path: '/prodavacProfil', query: { id: korisnik.id } });
        }
      },
      nazad() {
        if (this.$route.query.role === 'kupac') {
          this.$router.push('/homeKupac');
        } else if (this.$route.query.role === 'prodavac') {
          this.$router.push('/homeProdavac');
        } else {
          this.$router.push('/');
        }
      }
    }
  };
  </script>
  
  <style>
  #pregledKorisnika {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  #pregledKorisnika h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  table {
    width: 100%;
    border-collapse: collapse;
  }
  table, th, td {
    border: 1px solid #ddd;
  }
  th, td {
    padding: 8px;
    text-align: left;
  }
  th {
    background-color: #f2f2f2;
  }
  .button {
    padding: 10px 20px;
    background-color: #17a2b8;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 4px;
  }
  button:hover {
    background-color: #17a2b8;
    color: white;
  }
  </style>
  