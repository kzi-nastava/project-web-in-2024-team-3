<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="korisnickoIme">Korisničko Ime:</label>
        <input
          type="text"
          id="korisnickoIme"
          v-model="loginDto.korisnickoIme"
          required
        />
      </div>
      <div class="form-group">
        <label for="lozinka">Lozinka:</label>
        <input
          type="password"
          id="lozinka"
          v-model="loginDto.lozinka"
          required
        />
      </div>
      <button type="submit">Login</button>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from "axios";
import { useRouter } from "vue-router";

export default {
data() {
  return {
    loginDto: {
      korisnickoIme: "",
      lozinka: "",
    },
    errorMessage: "",
  };
},
setup() {
  const router = useRouter();
  return { router };
},
methods: {
  async login() {
    try {
      const response = await axios.post("http://localhost:8081/api/login", this.loginDto);
      if (response.data.korisnik) {
        const uloga = response.data.korisnik.uloga;
        localStorage.setItem('ulogovanKorisnik', JSON.stringify(response.data.korisnik));
        if (uloga === "ADMIN") {
          this.$router.push("/homeAdmin");
        } else if (uloga === "KUPAC") {
          this.$router.push("/homeKupac");
        } else if (uloga === "PRODAVAC") {
          this.$router.push("/homeProdavac");
        }
      } else {
        this.errorMessage = "Greška pri prijavljivanju.";
      }
    } catch (error) {
      console.error("Login error:", error); 
      if (error.response) {
        this.errorMessage = error.response.data;
      } else {
        this.errorMessage = "Greška u komunikaciji sa serverom.";
      }
    }
  },
},
};
</script>

<style scoped>
.login-container {
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

.form-group input {
width: 100%;
padding: 0.5em;
border: 1px solid #ccc;
border-radius: 5px;
}

button {
padding: 0.7em 1.5em;
background-color: #17a2b8;
border: none;
color: white;
border-radius: 5px;
cursor: pointer;
}

button:hover {
  background-color: #138496;
}

p {
color: red;
margin-top: 1em;
}
</style>
