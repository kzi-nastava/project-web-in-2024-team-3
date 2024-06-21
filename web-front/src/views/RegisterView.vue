<template>
    <div class="register-container">
      <h1>Register</h1>
      <form @submit.prevent="register">
        <div class="form-group">
          <label for="ime">Ime:</label>
          <input
            type="text"
            id="ime"
            v-model="registerDto.ime"
            required
          />
        </div>
        <div class="form-group">
          <label for="prezime">Prezime:</label>
          <input
            type="text"
            id="prezime"
            v-model="registerDto.prezime"
            required
          />
        </div>
        <div class="form-group">
          <label for="korisnickoIme">Korisničko Ime:</label>
          <input
            type="text"
            id="korisnickoIme"
            v-model="registerDto.korisnickoIme"
            required
          />
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input
            type="email"
            id="email"
            v-model="registerDto.email"
            required
          />
        </div>
        <div class="form-group">
          <label for="brojTelefona">Broj Telefona:</label>
          <input
            type="text"
            id="brojTelefona"
            v-model="registerDto.brojTelefona"
            required
          />
        </div>
        <div class="form-group">
          <label for="lozinka">Lozinka:</label>
          <input
            type="password"
            id="lozinka"
            v-model="registerDto.lozinka"
            required
          />
        </div>
        <div class="form-group">
          <label for="potvrdaLozinke">Potvrda Lozinke:</label>
          <input
            type="password"
            id="potvrdaLozinke"
            v-model="registerDto.potvrdaLozinke"
            required
          />
        </div>
        <div class="form-group">
          <label for="uloga">Uloga:</label>
          <select id="uloga" v-model="registerDto.uloga" required>
            <option value="">Izaberite ulogu</option>
            <option value="kupac">Kupac</option>
            <option value="prodavac">Prodavac</option>
          </select>
        </div>
        <button type="submit">Register</button>
      </form>
      <p v-if="errorMessage">{{ errorMessage }}</p>
      <p v-if="successMessage">{{ successMessage }}</p>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import { useRouter } from "vue-router";
  
  export default {
    data() {
      return {
        registerDto: {
          ime: "",
          prezime: "",
          korisnickoIme: "",
          email: "",
          brojTelefona: "",
          lozinka: "",
          potvrdaLozinke: "",
          uloga: ""
        },
        errorMessage: "",
        successMessage: ""
      };
    },
    setup() {
      const router = useRouter();
      return { router };
    },
    methods: {
      async register() {
        try {
          const response = await axios.post("http://localhost:8081/api/register", this.registerDto);
          this.successMessage = response.data;
          this.errorMessage = "";
  
          // Redirekcija na osnovu uloge
          if (this.registerDto.uloga === "kupac") {
            this.$router.push("/homeKupac");
          } else if (this.registerDto.uloga === "prodavac") {
            this.$router.push("/homeProdavac");
          }
        } catch (error) {
          if (error.response) {
            this.errorMessage = error.response.data;
          } else {
            this.errorMessage = "Greška u komunikaciji sa serverom.";
          }
          this.successMessage = "";
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .register-container {
    max-width: 400px;
    margin: auto;
    padding: 1em;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f9f9f9;
  }
  
  .form-group {
    margin-bottom: 1em;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 0.5em;
  }
  
  .form-group input,
  .form-group select {
    width: 100%;
    padding: 0.5em;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  button {
    padding: 0.7em 1.5em;
    background-color: #007bff;
    border: none;
    color: white;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  p {
    margin-top: 1em;
  }
  </style>
  