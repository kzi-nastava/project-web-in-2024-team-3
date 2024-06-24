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
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="prezime">Prezime:</label>
        <input
          type="text"
          id="prezime"
          v-model="registerDto.prezime"
          required
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="korisnickoIme">Korisničko Ime:</label>
        <input
          type="text"
          id="korisnickoIme"
          v-model="registerDto.korisnickoIme"
          required
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input
          type="email"
          id="email"
          v-model="registerDto.email"
          required
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="brojTelefona">Broj Telefona:</label>
        <input
          type="text"
          id="brojTelefona"
          v-model="registerDto.brojTelefona"
          required
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="lozinka">Lozinka:</label>
        <input
          type="password"
          id="lozinka"
          v-model="registerDto.lozinka"
          required
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="potvrdaLozinke">Potvrda Lozinke:</label>
        <input
          type="password"
          id="potvrdaLozinke"
          v-model="registerDto.potvrdaLozinke"
          required
          class="form-control"
        />
      </div>
      <div class="form-group">
        <label for="uloga">Uloga:</label>
        <select id="uloga" v-model="registerDto.uloga" required class="form-control">
          <option value="">Izaberite ulogu</option>
          <option value="kupac">Kupac</option>
          <option value="prodavac">Prodavac</option>
        </select>
      </div>
      <button type="submit" class="btn btn-primary">Register</button>
    </form>
    <p v-if="errorMessage" class="mt-3 text-danger">{{ errorMessage }}</p>
    <p v-if="successMessage" class="mt-3 text-success">{{ successMessage }}</p>
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

.btn-primary {
  padding: 0.7em 1.5em;
  background-color: #17a2b8;
  border: none;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #138496;
  border-color: #117a8b;
}

.text-danger {
  color: #dc3545;
}

.text-success {
  color: #28a745;
}
</style>
