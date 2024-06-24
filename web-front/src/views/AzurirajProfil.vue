<template>
    <div id="azurirajProfil">
      <h1>Ažuriraj Profil</h1>
      <form @submit.prevent="azurirajProfil">
        <div>
          <label for="ime">Ime:</label>
          <input type="text" v-model="updateDto.ime" id="ime" />
        </div>
        <div>
          <label for="prezime">Prezime:</label>
          <input type="text" v-model="updateDto.prezime" id="prezime" />
        </div>
        <div>
          <label for="brojTelefona">Broj Telefona:</label>
          <input type="text" v-model="updateDto.brojTelefona" id="brojTelefona" />
        </div>
        <div>
          <label for="datumRodjenja">Datum Rođenja:</label>
          <input type="date" v-model="updateDto.datumRodjenja" id="datumRodjenja" />
        </div>
        <div>
          <label for="profilnaSlika">Profilna Slika:</label>
          <input type="file" @change="onFileChange" id="profilnaSlika" />
        </div>
        <div>
          <label for="opis">Opis:</label>
          <textarea v-model="updateDto.opis" id="opis"></textarea>
        </div>
        <div>
          <label for="staraLozinka">Trenutna Lozinka:</label>
          <input type="password" v-model="updateDto.staraLozinka" id="staraLozinka" required />
        </div>
        <div>
          <label for="novaLozinka">Nova Lozinka:</label>
          <input type="password" v-model="updateDto.novaLozinka" id="novaLozinka" />
        </div>
        <div>
          <label for="korisnickoIme">Korisničko Ime:</label>
          <input type="text" v-model="updateDto.korisnickoIme" id="korisnickoIme" />
        </div>
        <div>
          <label for="email">Email:</label>
          <input type="email" v-model="updateDto.email" id="email" />
        </div>
        <button type="submit">Ažuriraj Profil</button>
        <button type="button" @click="nazad">Nazad</button>
      </form>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "AzurirajProfil",
    data() {
      return {
        updateDto: {
          ime: "",
          prezime: "",
          brojTelefona: "",
          datumRodjenja: null,
          profilnaSlika: "",
          opis: "",
          staraLozinka: "",
          novaLozinka: "",
          korisnickoIme: "",
          email: ""
        },
        successMessage: "",
        errorMessage: "",
        userRole: null
      };
    },
    mounted() {
      this.userRole = this.$route.query.role;
    },
    methods: {
      onFileChange(event) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onload = (e) => {
          this.updateDto.profilnaSlika = e.target.result;
        };
        reader.readAsDataURL(file);
      },
      azurirajProfil() {
        axios
          .put("http://localhost:8081/api/ulogovan-korisnik/azuriraj", this.updateDto, { withCredentials: true })
          .then((res) => {
            this.successMessage = res.data;
            this.errorMessage = "";
          })
          .catch((err) => {
            this.errorMessage = err.response.data;
            this.successMessage = "";
          });
      },
      nazad() {
        if (this.userRole === 'kupac') {
          this.$router.push('/homeKupac');
        } else if (this.userRole === 'prodavac') {
          this.$router.push('/homeProdavac');
        } else {
          this.$router.push('/');
        }
      }
    }
  };
  </script>
  
  <style>
  #azurirajProfil {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  #azurirajProfil h1 {
    text-align: center;
    margin-bottom: 20px;
  }
  #azurirajProfil form {
    display: flex;
    flex-direction: column;
  }
  #azurirajProfil form div {
    margin-bottom: 10px;
  }
  #azurirajProfil form label {
    display: block;
    margin-bottom: 5px;
  }
  #azurirajProfil form input,
  #azurirajProfil form textarea,
  #azurirajProfil form button {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 100%;
  }
  #azurirajProfil form button {
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    margin-top: 10px;
  }
  #azurirajProfil form button:hover {
    background-color: #0056b3;
  }
  #azurirajProfil form button[type="button"] {
    background-color: #ccc;
    margin-top: 0;
  }
  #azurirajProfil form button[type="button"]:hover {
    background-color: #999;
  }
  .success {
    color: green;
    text-align: center;
    margin-top: 20px;
  }
  .error {
    color: red;
    text-align: center;
    margin-top: 20px;
  }
  </style>
  