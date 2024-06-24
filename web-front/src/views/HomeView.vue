<template>
  <div id="sviProizvodi" class="container mt-5">
    <!-- Gornji deo sa logom i dugmadima -->
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <img src="images/ShopLogo.jpg" alt="Logo" class="logo img-fluid" />
      </div>
      <div>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="register">Registracija</button>
        <button class="btn btn-outline-primary btn-sm" @click="login">Prijava</button>
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
            <option value="FIKSNACENA">Fiksna cena</option>
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

export default {
  name: "HomeView",
  data() {
    return {
      proizvodi: [],
      filtriraniProizvodi: [],
      paginiraniProizvodi: [],
      pretraga: "",
      cenaOd: null,
      cenaDo: null,
      tipProdaje: "",
      kategorijaNaziv: "",
      kategorije: [],
      filtriraneKategorije: [],
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
      this.$router.push({ path: "/proizvod", query: { id: proizvod.id, role: "visitor" } });
    },
    login() {
      this.$router.push("/login");
    },
    register() {
      this.$router.push("/register");
    },
    pretrazi() {
      if (this.pretraga.trim() !== "") {
        let pretragaLower = this.pretraga.toLowerCase().trim();
        this.filtriraniProizvodi = this.proizvodi.filter(
          (proizvod) =>
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
        params.min = this.cenaOd;
      }
      if (this.cenaDo !== null) {
        params.max = this.cenaDo;
      }
      if (this.tipProdaje !== "") {
        params.tipProdaje = this.tipProdaje;
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
          this.updatePagination();
          if (res.data.length === 0) {
            console.warn("Server je vratio prazan odgovor.");
          }
        })
        .catch((err) => {
          console.error("Filtriranje Error:", err);
        });
    },
    updatePagination() {
      this.totalPages = Math.ceil(this.filtriraniProizvodi.length / this.itemsPerPage);
      this.currentPage = Math.min(this.currentPage, this.totalPages);
      this.paginiraniProizvodi = this.filtriraniProizvodi.slice(
        (this.currentPage - 1) * this.itemsPerPage,
        this.currentPage * this.itemsPerPage
      );
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
  display: block;
  margin: 0 auto;
  max-width: 200px;
  max-height: 100px;
  width: auto;
  height: auto;
}

.card {
  background-color: #f8f9fa;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-control {
  padding: 0.375rem 0.
  .75rem;
  font-size: 1rem;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.input-group-append .btn {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}

.table {
  background-color: #fff;
}

.table th,
.table td {
  border: 1px solid #dee2e6;
  padding: 0.75rem;
  vertical-align: top;
}

.table th {
  background-color: #f2f2f2;
}

.btn {
  padding: 0.375rem 0.75rem;
  font-size: 0.875rem;
  line-height: 1.5;
  border-radius: 0.25rem;
  cursor: pointer;
}

.btn-primary {
  color: #fff;
  background-color: #17a2b8;
  border-color: #17a2b8;
}

.btn-primary:hover {
  color: #fff;
  background-color: #17a2b8;
  border-color: #17a2b8
}

.btn-secondary {
  color: #fff;
  background-color: #6c757d;
  border-color: #6c757d;
}

.btn-secondary:hover {
  color: #fff;
  background-color: #5a6268;
  border-color: #545b62;
}

.btn-info {
  color: #fff;
  background-color: #17a2b8;
  border-color: #17a2b8;
}

.btn-info:hover {
  color: #fff;
  background-color: #138496;
  border-color: #117a8b;
}

.btn-outline-secondary {
  color: #6c757d;
  border-color: #6c757d;
}

.btn-outline-secondary:hover {
  color: #fff;
  background-color: #6c757d;
  border-color: #6c757d;
}

.pagination {
  justify-content: center;
}

.pagination .btn {
  margin: 0 2px;
}

.container-buttons {
  text-align: right;
  margin-top: 10px;
}
</style>
