<template>
  <div id="kupacProizvodi" class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <img src="images/ShopLogo.jpg" alt="Logo" class="logo img-fluid" />
      </div>
      <div>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="azurirajProfil">Ažuriraj profil</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="pregledKorisnika">Pregledaj korisnike</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="oceniProdavca">Oceni prodavca</button>
        <button class="btn btn-outline-secondary mr-2 btn-sm" @click="prijaviProdavca">Prijavi prodavca</button>
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
          <th>Akcije</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="proizvod in paginiraniProizvodi" :key="proizvod.id">
          <td>{{ proizvod.naziv }}</td>
          <td>{{ proizvod.cena }}</td>
          <td>
            <button class="btn btn-info btn-sm" @click="seeMore(proizvod)">Prikazi više</button>
          </td>
          <td>
            <button class="btn btn-info btn-sm" v-if="proizvod.tipProdaje === 'FIKSNACENA'" @click="kupiProizvod(proizvod)">Kupi proizvod</button>
            <div v-if="proizvod.tipProdaje === 'AUKCIJA'">
              <input
                type="number"
                v-model.number="ponudaCene[proizvod.id]"
                placeholder="Unesite ponudu"
              />
              <button class="btn btn-primary btn-sm" @click="napraviPonudu(proizvod.id)">Napravi ponudu</button>
              <button class="btn btn-secondary btn-sm mt-1" @click="prikaziPonude(proizvod.id)">Vidi ponude</button>
            </div>
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
  name: "KupacHomeView",
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
      ponudaCene: {}
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
      this.$router.push({ path: '/proizvod', query: { id: proizvod.id, role: 'kupac' } });
    },
    kupiProizvod(proizvod) {
      axios
        .post(`http://localhost:8081/api/proizvod-fiskna/${proizvod.id}`, {}, { withCredentials: true })
        .then((res) => {
          this.successMessage = "Proizvod uspešno kupljen!";
          this.errorMessage = "";
          this.filtriraniProizvodi = this.filtriraniProizvodi.filter(p => p.id !== proizvod.id);
          this.updatePagination();
        })
        .catch((err) => {
          this.errorMessage = "Greška pri kupovini proizvoda: " + err.response.data;
          this.successMessage = "";
          console.error("Error:", err);
        });
    },
    napraviPonudu(proizvodId) {
      const cena = this.ponudaCene[proizvodId];
      if (cena) {
        axios
          .post(`http://localhost:8081/api/proizvod-aukcija/${proizvodId}`, null, {
            params: { cena: parseFloat(cena) },
            withCredentials: true
          })
          .then((res) => {
            this.successMessage = "Ponuda uspešno poslata!";
            this.errorMessage = "";
            this.ponudaCene[proizvodId] = ""; 
            this.ucitajProizvode(); 
          })
          .catch((err) => {
            if (err.response && err.response.status === 400) {
              this.errorMessage = "Greška pri slanju ponude: Niste prijavljeni ili ponuda nije validna.";
            } else if (err.response && err.response.status === 500) {
              this.errorMessage = "Već imate najveću ponudu.";
            } else {
              this.errorMessage = "Greška pri slanju ponude: " + err.response.data;
            }
            this.successMessage = "";
            console.error("Error:", err);
          });
      } else {
        this.errorMessage = "Morate uneti cenu za ponudu.";
        this.successMessage = "";
      }
    },
    prikaziPonude(proizvodId) {
      this.$router.push({ path: '/ponude', query: { id: proizvodId } });
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
        this.filtriraniProizvodi = [...this.proizvodi]
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

      axios
        .get(`http://localhost:8081/api/proizvodi/filtrirajProizvode`, {
          params: params,
          withCredentials: true,
        })
        .then((res) => {
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
    azurirajProfil() {
      this.$router.push({ path: '/azurirajProfil', query: { role: 'kupac' } });
    },
    pregledKorisnika() {
      this.$router.push({ path: '/pregledKorisnika', query: { role: 'kupac' } });
    },
    oceniProdavca() {
      this.$router.push("/oceniProdavca");
    },
    prijaviProdavca() {
      this.$router.push("/prijaviProdavca");
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