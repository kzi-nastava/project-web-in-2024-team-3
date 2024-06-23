<template>
    <div id="proizvodView">
      <img src="images/ShopLogo.jpg" alt="Logo" class="logo"/>
      <br /><br />
      <div class="kontejner-proizvod" v-if="proizvod">
        <h2>{{ proizvod.naziv }}</h2>
        <p><strong>Cena:</strong> {{ proizvod.cena }} RSD</p>
        <p><strong>Opis:</strong> {{ proizvod.opis }}</p>
        <p><strong>Kategorija:</strong> {{ proizvod.kategorija.naziv }}</p>
        <p><strong>Tip prodaje:</strong> {{ proizvod.tipProdaje }}</p>
        <button @click="nazad">Nazad</button>
      </div>
      <div v-else>
        <p>Učitavanje proizvoda...</p>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "ProizvodView",
    data() {
      return {
        proizvod: null,
        userRole: null, 
      };
    },
    mounted() {
      this.ucitajProizvod();
      this.userRole = this.$route.query.role; 
    },
    methods: {
      ucitajProizvod() {
        const id = this.$route.query.id;
        axios
          .get(`http://localhost:8081/api/proizvodi/${id}`, { withCredentials: true })
          .then((res) => {
            console.log("Proizvod Response:", res.data);
            this.proizvod = res.data;
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      },
      nazad() {
        if (this.userRole === 'admin') {
          this.$router.push('/homeAdmin');
        } else if (this.userRole === 'prodavac') {
          this.$router.push('/homeProdavac');
        } else if (this.userRole === 'kupac') {
          this.$router.push('/homeKupac');
        } else {
          this.$router.push('/');
        }
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
  .kontejner-proizvod {
    margin: 20px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    max-width: 600px;
    background-color: white;
    color: black;
  }
  .kontejner-proizvod h2 {
    margin-bottom: 20px;
  }
  .kontejner-proizvod p {
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
  