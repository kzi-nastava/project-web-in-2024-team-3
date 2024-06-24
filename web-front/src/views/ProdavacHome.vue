<template>
  <div id="prodavacProizvodi" class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <img src="images/ShopLogo.jpg" alt="Logo" class="logo img-fluid" />
      </div>
      <div>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="azurirajProfil">Ažuriraj profil</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="pregledKorisnika">Pregledaj korisnike</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="postaviProizvod">Postavi proizvod</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="oceniKupca">Oceni kupca</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="prijaviKupca">Prijavi kupca</button>
        <button class="btn btn-outline-primary btn-sm" @click="logout">Izloguj se</button>
      </div>
    </div>

    <div class="card p-3 mb-4">
      <!-- Pretraga po nazivu ili opisu -->
      <div class="input-group mb-3">
        <input class="form-control" type="text" v-model="pretraga" placeholder="Pretraži proizvode po nazivu ili opisu" />
        <div class="input-group-append">
          <button class="btn btn-primary" @click="pretrazi">Pretraži</button>
        </div>
      </div>

      <!-- Filtriranje -->
      <div class="form-row">
        <div class="col-md-3 mb-3">
          <label for="cenaOd">Cena od:</label>
          <input id="cenaOd" type="number" class="form-control" v-model.number="cenaOd" />
        </div>
        <div class="col-md-3 mb-3">
          <label for="cenaDo">do:</label>
          <input id="cenaDo" type="number" class="form-control" v-model.number="cenaDo" />
        </div>
        <div class="col-md-3 mb-3">
          <label for="tipProdaje">Tip prodaje:</label>
          <select id="tipProdaje" class="form-control" v-model="tipProdaje">
            <option value="">Svi tipovi</option>
            <option value="FIKSNA_CENA">Fiksna cena</option>
            <option value="AUKCIJA">Aukcija</option>
          </select>
        </div>
        <div class="col-md-3 mb-3">
          <label for="kategorija">Kategorija:</label>
          <select id="kategorija" class="form-control" v-model="kategorijaNaziv">
            <option value="">Sve kategorije</option>
            <option v-for="kategorija in filtriraneKategorije" :key="kategorija.id" :value="kategorija.naziv">
              {{ kategorija.naziv }}
            </option>
          </select>
        </div>
      </div>
      <button class="btn btn-secondary mt-2" @click="filtrirajProizvode">Filtriraj</button>
    </div>

    <table class="table table-striped">
      <thead>
        <tr>
          <th>Naziv</th>
          <th>Cena</th>
          <th>Vise</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="proizvod in paginiraniProizvodi" :key="proizvod.id">
          <td>{{ proizvod.naziv }}</td>
          <td>{{ proizvod.cena }}</td>
          <td>
            <button class="btn btn-info btn-sm" @click="seeMore(proizvod)">Prikazi više</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="d-flex justify-content-between align-items-center mt-4">
      <button class="btn btn-outline-primary btn-sm" @click="prevPage" :disabled="currentPage === 1">Prethodna</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button class="btn btn-outline-primary btn-sm" @click="nextPage" :disabled="currentPage === totalPages">Sledeća</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.withCredentials = true;

export default {
  name: "ProdavacHome",
  data() {
    return {
      proizvodi: [],
      pretraga: '',
      cenaOd: null,
      cenaDo: null,
      tipProdaje: '',
      kategorijaNaziv: '',
      kategorije: [],
      filtriraneKategorije: [],
      filtriraniProizvodi: [],
      paginiraniProizvodi: [],
      currentPage: 1,
      itemsPerPage: 5,
      totalPages: 1,
    };
  },
  mounted() {
    this.ucitajProizvode();
    this.ucitajSveKategorije();
  },
  methods: {
    ucitajProizvode() {
      axios
        .get("http://localhost:8081/api/proizvodi", { withCredentials: true })
        .then((res) => {
          console.log("Response:", res.data);
          this.proizvodi = res.data;
          this.filtriraniProizvodi = res.data;
          this.updatePagination();
        })
        .catch((err) => {
          console.error("Error:", err);
        });
    },
    ucitajSveKategorije() {
      axios
        .get("http://localhost:8081/kategorije", { withCredentials: true })
        .then((res) => {
          console.log("Sve kategorije Response:", res.data);
          this.kategorije = res.data;
          this.filtriraneKategorije = res.data;
        })
        .catch((err) => {
          console.error("Sve kategorije Error:", err);
        });
    },
    seeMore(proizvod) {
      this.$router.push({ path: '/proizvod', query: { id: proizvod.id, role: 'prodavac' } });
    },
    azurirajProfil() {
      this.$router.push({ path: '/azurirajProfil', query: { role: 'prodavac' } });
    },
    pregledKorisnika() {
      this.$router.push({ path: '/pregledKorisnika', query: { role: 'prodavac' } });
    },
    postaviProizvod() {
      this.$router.push("/postaviProizvod");
    },
    oceniKupca() {
      // Implementacija funkcionalnosti
    },
    prijaviKupca() {
      // Implementacija funkcionalnosti
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
    pretrazi() {
      if (this.pretraga.trim() !== "") {
        let pretragaLower = this.pretraga.toLowerCase().trim();
        this.filtriraniProizvodi = this.proizvodi.filter((proizvod) =>
          proizvod.naziv.toLowerCase().includes(pretragaLower) ||
          proizvod.opis.toLowerCase().includes(pretragaLower)
        );
      } else {
        this.filtriraniProizvodi = [...this.proizvodi];
      }
      this.updatePagination();
    },
    filtrirajProizvode() {
      let params = {};
      if (this.cenaOd !== null) {
        params.cenaOd = this.cenaOd;
      }
      if (this.cenaDo !== null) {
        params.cenaDo = this.cenaDo;
      }
      if (this.tipProdaje !== "") {
        params.tip = this.tipProdaje;
      }
      if (this.kategorijaNaziv !== "") {
        params.kategorija = this.kategorijaNaziv;
      }

      console.log("Parametri za filtriranje:", params);

      axios
        .get(`http://localhost:8081/api/proizvodi/filtrirajProizvode`, {
params: params,
withCredentials: true,
})
.then((res) => {
console.log("Filtriranje Response:", res.data);
this.filtriraniProizvodi = res.data;
if (res.data.length === 0) {
console.warn("Server je vratio prazan odgovor.");
}
this.updatePagination(); // Dodajemo poziv za ažuriranje paginacije nakon filtriranja
})
.catch((err) => {
console.error("Filtriranje Error:", err);
});
},
nextPage() {
if (this.currentPage < this.totalPages) {
this.currentPage++;
this.updatePagination();
}
},
prevPage() {
if (this.currentPage > 1) {
this.currentPage--;
this.updatePagination();
}
},
updatePagination() {
this.totalPages = Math.ceil(this.filtriraniProizvodi.length / this.itemsPerPage);
this.currentPage = Math.min(this.currentPage, this.totalPages);
const startIndex = (this.currentPage - 1) * this.itemsPerPage;
this.paginiraniProizvodi = this.filtriraniProizvodi.slice(
startIndex,
startIndex + this.itemsPerPage
);
},
},
watch: {
currentPage() {
this.updatePagination();
},
},
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
