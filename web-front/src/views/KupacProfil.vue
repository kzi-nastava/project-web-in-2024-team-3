<template>
  <div id="kupacProfil" class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <img src="images/ShopLogo.jpg" alt="Logo" class="logo img-fluid" />
      </div>
      <div>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="nazad">Nazad</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="oceniKupca">Oceni Kupca</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="prijaviKupca">Prijavi Kupca</button>
      </div>
    </div>

    <div class="card p-3 mb-4">
      <h1 class="text-center mb-4">Profil Kupca</h1>
      <div v-if="kupac">
        <p><strong>Ime:</strong> {{ kupac.ime }}</p>
        <p><strong>Prezime:</strong> {{ kupac.prezime }}</p>
        <p><strong>Email:</strong> {{ kupac.email }}</p>
        <p><strong>Prosečna ocena:</strong> {{ kupac.prosecnaOcena }}</p>
        <h2 class="text-center mb-3">Proizvodi koje je kupio</h2>
        <ul>
          <li v-for="proizvod in kupljeniProizvodi" :key="proizvod.id">{{ proizvod.naziv }}</li>
        </ul>
        <h2 class="text-center mb-3">Recenzije</h2>
        <ul>
          <li v-for="recenzija in kupac.recenzije" :key="recenzija.id">
            <p><strong>Prodavac:</strong> {{ recenzija.prodavacIme }}</p>
            <p><strong>Ocena:</strong> {{ recenzija.ocena }}</p>
            <p><strong>Komentar:</strong> {{ recenzija.komentar }}</p>
          </li>
        </ul>
      </div>
    </div>
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
      // Implementacija funkcionalnosti za ocenjivanje kupca
    },
    prijaviKupca() {
      // Implementacija funkcionalnosti za prijavu kupca
    }
  }
};
</script>

<style scoped>
.logo {
  max-width: 200px;
  max-height: 100px;
  width: auto;
  height: auto;
}

.container {
  background-color: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
}

.btn {
  font-size: 14px;
}

.input-group-append .btn {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}

.table {
  background-color: white;
}

.card {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-control {
  border-radius: 4px;
}

.form-row {
  align-items: center;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}

.btn-outline-primary {
  color: #007bff;
  border-color: #007bff;
}

.btn-outline-secondary {
  color: #6c757d;
  border-color: #6c757d;
}

.btn-info {
  background-color: #17a2b8;
  color: white;
}

.btn-sm {
  font-size: 12px;
}

.btn-sm:not(:last-child) {
  margin-right: 8px;
}

.btn-sm:not(:first-child) {
  margin-left: 8px;
}

.table th,
.table td {
  vertical-align: middle;
}
</style>