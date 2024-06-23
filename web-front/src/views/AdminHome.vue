<template>
  <div id="adminPanel">
    <img src="images/ShopLogo.jpg" alt="Logo" class="logo" />
    <br /><br />
    <div class="kontejner-admin">
      <h2>Administratorski panel</h2>
      
      <div class="admin-section">
        <h3>Recenzije</h3>
        <ul>
          <li v-for="recenzija in recenzije" :key="recenzija.id">
            <p><strong>Korisnik:</strong> {{ recenzija.korisnik.korisnickoIme }}</p>
            <p><strong>Ocena:</strong> {{ recenzija.ocena }}</p>
            <p><strong>Komentar:</strong> {{ recenzija.komentar }}</p>
            <button @click="izmeniRecenziju(recenzija.id)">Izmeni</button>
            <button @click="obrisiRecenziju(recenzija.id)">Obriši</button>
          </li>
        </ul>
      </div>

      <div class="admin-section">
        <h3>Prijave</h3>
        <ul>
          <li v-for="prijava in prijave" :key="prijava.id">
            <p><strong>Razlog:</strong> {{ prijava.razlogZaPrijavu }}</p>
            <p><strong>Podneo prijavu:</strong> {{ prijava.podneoPrijavu.korisnickoIme }}</p>
            <button @click="prihvatiPrijavu(prijava.id)">Prihvati</button>
            <button @click="odbijPrijavu(prijava.id)">Odbij</button>
          </li>
        </ul>
      </div>
    </div>
    <br />
    <div class="container-buttons">
      <button @click="logout">Izloguj se</button>
    </div>

    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showEditModal = false">&times;</span>
        <h2>Izmena Recenzije</h2>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="korisnickoIme">Korisnik:</label>
            <input type="text" id="korisnickoIme" v-model="selectedRecenzija.korisnik.korisnickoIme" disabled />
          </div>
          <div class="form-group">
            <label for="komentar">Komentar:</label>
            <textarea id="komentar" v-model="selectedRecenzija.komentar" required></textarea>
          </div>
          <button type="submit">Sačuvaj izmene</button>
          <button type="button" @click="showEditModal = false">Otkaži</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminHome",
  data() {
    return {
      recenzije: [],
      prijave: [],
      showEditModal: false,
      selectedRecenzija: null,
      errorMessage: ''
    };
  },
  mounted() {
    this.ucitajRecenzije();
    this.ucitajPrijave();
  },
  methods: {
    ucitajRecenzije() {
      axios
        .get("http://localhost:8081/recenzije", { withCredentials: true })
        .then((res) => {
          this.recenzije = res.data;
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    ucitajPrijave() {
      axios
        .get("http://localhost:8081/prijave", { withCredentials: true })
        .then((res) => {
          this.prijave = res.data;
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    izmeniRecenziju(id) {
      const recenzija = this.recenzije.find(r => r.id === id);
      if (recenzija) {
        this.selectedRecenzija = { ...recenzija };
        this.showEditModal = true;
      }
    },
    obrisiRecenziju(id) {
      axios
        .delete(`http://localhost:8081/obrisi-recenziju/${id}`, { withCredentials: true })
        .then(() => {
          this.recenzije = this.recenzije.filter(recenzija => recenzija.id !== id);
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    prihvatiPrijavu(id) {
      axios
        .post(`http://localhost:8081/api/prihvati-prijavu/${id}`, {}, { withCredentials: true })
        .then(() => {
          this.prijave = this.prijave.filter(prijava => prijava.id !== id);
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    odbijPrijavu(id) {
      const razlog = prompt("Unesite razlog odbijanja prijave:");
      if (razlog) {
        axios
          .post(`http://localhost:8081/api/odbij-prijavu/${id}`, { razlog: razlog }, { withCredentials: true })
          .then(() => {
            this.prijave = this.prijave.filter(prijava => prijava.id !== id);
          })
          .catch((err) => {
            console.error("Error:", err);
          });
      }
    },
    logout() {
      axios
        .post("http://localhost:8081/api/logout", {}, { withCredentials: true })
        .then(() => {
          this.$router.push("/");
        })
        .catch((err) => {
          console.error("Logout Error:", err);
          if (err.response && err.response.status === 403) {
            alert("Niste ulogovani ili nemate privilegije za ovu akciju.");
          } else {
            alert("Došlo je do greške prilikom pokušaja logout-a.");
          }
        });
    },
    submitForm() {
      axios
        .put(`http://localhost:8081/api/izmeni-recenziju/${this.selectedRecenzija.id}`, {
          komentar: this.selectedRecenzija.komentar
        }, { withCredentials: true })
        .then(() => {
          alert("Recenzija uspešno izmenjena!");
          this.showEditModal = false;
          this.ucitajRecenzije();
        })
        .catch((err) => {
          console.error("Error:", err);
          this.errorMessage = "Došlo je do greške prilikom izmene recenzije.";
        });
    }
  },
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
.kontejner-admin {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.admin-section {
  margin-bottom: 20px;
}
.admin-section h3 {
  color: #007bff;
}
.admin-section ul {
  list-style-type: none;
  padding: 0;
}
.admin-section li {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.admin-section button {
  margin-right: 10px;
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}
.admin-section button:hover {
  background-color: #0056b3;
}
.container-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
button {
  margin-left: 10px;
  padding: 10px 20px;
  background-color: #fdfeff;
  color: #080000;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}
button:hover {
  background-color: #c0c0c0;
}
body {
  background-color: #dcdcdc;
  color: white;
  font-family: Arial, sans-serif;
}
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  position: relative;
}
.close {
  position: absolute;
  top: 10px;
  right: 20px;
  font-size: 30px;
  cursor: pointer;
}
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  display: block;
  margin-bottom: 5px;
}
.form-group input,
.form-group textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
button {
  padding: 10px 15px;
  margin-right: 10px;
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
