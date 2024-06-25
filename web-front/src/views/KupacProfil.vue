<template>
    <div id="kupacProfil">
      <h1>Profil Kupca</h1>
      <div v-if="kupac">
        <p><strong>Ime:</strong> {{ kupac.ime }}</p>
        <p><strong>Prezime:</strong> {{ kupac.prezime }}</p>
        <p><strong>Email:</strong> {{ kupac.email }}</p>
        <p><strong>Prosečna ocena:</strong> {{ kupac.prosecnaOcena }}</p>
        <h2>Proizvodi koje je kupio</h2>
        <ul>
          <li v-for="proizvod in kupljeniProizvodi" :key="proizvod.id">{{ proizvod.naziv }}</li>
        </ul>
        <h2>Recenzije</h2>
        <ul>
          <li v-for="recenzija in kupac.recenzije" :key="recenzija.id">
            <p><strong>Prodavac:</strong> {{ recenzija.prodavacIme }}</p>
            <p><strong>Ocena:</strong> {{ recenzija.ocena }}</p>
            <p><strong>Komentar:</strong> {{ recenzija.komentar }}</p>
          </li>
        </ul>
        <button @click="oceniKupca">Oceni Kupca</button>
        <button @click="prijaviKupca">Prijavi Kupca</button>
      </div>
      <button @click="nazad">Nazad</button>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "KupacProfil",
    data() {
      return {
        kupac: null,
        kupljeniProizvodi: [],
        ulogovanKorisnik: null,
      };
    },
    mounted() {
      this.ucitajKupca();
      this.ucitajKupljeneProizvode();
      this.ucitajUlogovanogKorisnika();
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
      ucitajKupljeneProizvode() {
        const id = this.$route.query.id;
        axios
          .get(`http://localhost:8081/kupac/${id}/proizvodi`, { withCredentials: true })
          .then((res) => {
            console.log("Kupljeni proizvodi:", res.data);
            this.kupljeniProizvodi = res.data;
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      },
      ucitajUlogovanogKorisnika() {
        this.ulogovanKorisnik = JSON.parse(localStorage.getItem('ulogovanKorisnik'));
      },
      nazad() {
        if (this.ulogovanKorisnik && this.ulogovanKorisnik.uloga === 'PRODAVAC') {
          this.$router.push({ path: '/pregledKorisnika', query: { role: 'prodavac' } });
        } else if (this.ulogovanKorisnik && this.ulogovanKorisnik.uloga === 'KUPAC') {
          this.$router.push({ path: '/pregledKorisnika', query: { role: 'kupac' } });
        } else {
          this.$router.push('/');
        }
      },
      oceniKupca() {
        const id = this.$route.query.id;
            this.$router.push({ path: '/oceniKupca', query: { id } });
      },
      prijaviKupca() {
        const id = this.$route.query.id;
            this.$router.push({ path: '/prijaviKupca', query: { id } });
      }
    }
  };
  </script>
  
  <style>
  #kupacProfil {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  #kupacProfil h1, #kupacProfil h2 {
    text-align: center;
    margin-bottom: 20px;
  }
  p, ul {
    margin: 10px 0;
  }
  button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 4px;
  }
  button:hover {
    background-color: #0056b3;
  }
  </style>
  