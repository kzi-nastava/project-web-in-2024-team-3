<template>
  <div id="prodavacProizvodi">
    <img src="images/ShopLogo.jpg" alt="Logo" class="logo" />
    <br /><br />
    <div class="kontejner-proizvodi">
      <!-- Pretraga po nazivu ili opisu -->
      <input class="input" type="text" v-model="pretraga" placeholder="Pretraži proizvode po nazivu ili opisu" />
      <button @click="pretrazi">Pretraži</button>
      
      <!-- Filtriranje -->
      <div class="filtriranje">
        <label class="od">Cena od:</label>
        <input type="number" v-model.number="cenaOd" />

        <label class="do">do:</label>
        <input type="number" v-model.number="cenaDo" />
        
        <label class="tip">Tip prodaje:</label>
        <select v-model="tipProdaje">
          <option value="">Svi tipovi</option>
          <option value="FIKSNA_CENA">Fiksna cena</option>
          <option value="AUKCIJA">Aukcija</option>
        </select>
        
        <!-- Dropdown za kategorije -->
        <label class="kategorija">Kategorija:</label>
        <select v-model="kategorijaNaziv">
          <option value="">Sve kategorije</option>
          <option v-for="kategorija in filtriraneKategorije" :key="kategorija.id" :value="kategorija.naziv">{{ kategorija.naziv }}</option>
        </select>
        <button @click="filtrirajProizvode">Filtriraj</button>
      </div>
      
      <table id="proizvodi">
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
              <button class="btnSeeMore" @click="seeMore(proizvod)">
                Prikazi više
              </button>
            </td>
            <td>
              <button class="btn btn-warning btn-sm" @click="azurirajProizvod(proizvod.id)">
                Ažuriraj proizvod
              </button>
              <button v-if="proizvod.tipProdaje === 'AUKCIJA'" class="btn btn-danger btn-sm" @click="zavrsiAukciju(proizvod.id)">
                Završetak aukcije
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <br />
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">Prethodna</button>
      <span>Stranica {{ currentPage }} od {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Sledeća</button>
    </div>
    <br />
    <div class="container-buttons">
      <button @click="azurirajProfil">Ažuriraj profil</button>
      <button @click="pregledKorisnika">Pregledaj korisnike</button>
      <button @click="postaviProizvod">Postavi proizvod</button>
      <button @click="logout">Izloguj se</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.withCredentials = true;

export default {
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
    azurirajProizvod(id) {
      this.$router.push({ name: "azuriranjeProizvoda", query: { id: id } });
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
          if (res.data.length === 0) {
            console.warn("Server je vratio prazan odgovor.");
          }
          this.updatePagination();
        })
        .catch((err) => {
          console.error("Filtriranje Error:", err);
        });
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
    zavrsiAukciju(id) {
      if (confirm("Da li ste sigurni da želite da završite aukciju za ovaj proizvod?")) {
        axios.post(`http://localhost:8081/prodavac/krajaukcije/${id}`, {}, { withCredentials: true })
          .then(response => {
            alert("Aukcija je uspešno završena!");
            this.filtriraniProizvodi = this.filtriraniProizvodi.filter(proizvod => proizvod.id !== id);
            this.updatePagination();
          })
          .catch(error => {
            console.error("Error finishing auction:", error);
            alert(error.response.data || "Greška pri završavanju aukcije.");
          });
      }
    },
    prikaziPonude(proizvodId) {
      this.$router.push(`/ponude?id=${proizvodId}`);
    },
    postaviProizvod() {
      this.$router.push("/postaviProizvod");
    },
    azurirajProfil() {
      this.$router.push({ path: '/azurirajProfil', query: { role: 'prodavac' } });
    },
    pregledKorisnika() {
      this.$router.push({ path: '/pregledKorisnika', query: { role: 'prodavac' } });
    },
    pregledRecenzija() {
      this.$router.push("/recenzijeProdavac");
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

<style>
.logo {
  display: block;
  margin: 0 auto;
  max-width: 200px;
  max-height: 100px;
  width: auto;
  height: auto;
}
.od {
  padding-left: auto;
}
.do {
  margin-left: 60px;
}
.kategorija {
  margin-left: 60px;
}
.tip {
  margin-left: 60px;
}
.input {
  width: 220px;
  height: 20px;
  margin-bottom: 10px;
}
.kontejner-proizvodi {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.filtriranje {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  margin-top: 20px;
}
.filtriranje label {
  margin-right: 10px;
}
.filtriranje input,
.filtriranje select,
.filtriranje button {
  margin-left: 10px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.filtriranje button {
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
.filtriranje button:hover {
  background-color: #0056b3;
}
#proizvodi {
  width: 80%;
  margin-top: 20px;
  border-collapse: collapse;
}
#proizvodi th,
#proizvodi td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
#proizvodi th {
  background-color: #f2f2f2;
}
#proizvodi tr:nth-child(even) {
  background-color: #f2f2f2;
}
#proizvodi tr:hover {
  background-color: #ddd;
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
</style>
