<template>
    <div id="prodavacProfil">
      <h1>Profil Prodavca</h1>
      <div v-if="prodavac">
        <p><strong>Ime:</strong> {{ prodavac.ime }}</p>
        <p><strong>Prezime:</strong> {{ prodavac.prezime }}</p>
        <p><strong>Email:</strong> {{ prodavac.email }}</p>
        <p><strong>Prosečna ocena:</strong> {{ prodavac.prosecnaOcena }}</p>
        <h2>Proizvodi koje prodaje</h2>
        <ul>
          <li v-for="proizvod in prodajeProizvode" :key="proizvod.id">{{ proizvod.naziv }}</li>
        </ul>
        <h2>Recenzije</h2>
        <ul>
          <li v-for="recenzija in prodavac.recenzije" :key="recenzija.id">
            <p><strong>Kupac:</strong> {{ recenzija.kupacIme }}</p>
            <p><strong>Ocena:</strong> {{ recenzija.ocena }}</p>
            <p><strong>Komentar:</strong> {{ recenzija.komentar }}</p>
          </li>
        </ul>
        <button @click="oceniProdavca">Oceni Prodavca</button>
        <button @click="prijaviProdavca">Prijavi Prodavca</button>
      </div>
      <button @click="nazad">Nazad</button>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "ProdavacProfil",
    data() {
      return {
        prodavac: null,
        prodajeProizvode: [],
        ulogovanKorisnik: null,
      };
    },
    mounted() {
      this.ucitajProdavca();
      this.ucitajProdajeProizvode();
      this.ucitajUlogovanogKorisnika();
    },
    methods: {
      ucitajProdavca() {
        const id = this.$route.query.id;
        axios
          .get(`http://localhost:8081/prodavac/${id}`, { withCredentials: true })
          .then((res) => {
            this.prodavac = res.data;
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      },
      ucitajProdajeProizvode() {
        const id = this.$route.query.id;
        axios
          .get(`http://localhost:8081/prodavac/${id}/proizvodi`, { withCredentials: true })
          .then((res) => {
            console.log("Prodaje proizvode:", res.data);
            this.prodajeProizvode = res.data;
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
      oceniProdavca() {
        // Implementacija funkcionalnosti za ocenjivanje prodavca
      },
      prijaviProdavca() {
        // Implementacija funkcionalnosti za prijavu prodavca
      }
    }
  };
  </script>
  
  <style>
  #prodavacProfil {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  #prodavacProfil h1, #prodavacProfil h2 {
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
  