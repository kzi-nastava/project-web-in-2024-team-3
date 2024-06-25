import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import HomeAdminView from '../views/AdminHome.vue'
import HomeProdavacView from '../views/ProdavacHome.vue'
import HomeKupacView from '../views/KupacHome.vue'
import RegisterView from '../views/RegisterView.vue'
import ProizvodView from '../views/ProizvodView.vue'
import AzurirajProfil from '../views/AzurirajProfil.vue'
import PregledKorisnika from '../views/PregledKorisnika.vue'
import PostaviProizvod from '../views/PostaviProizvod.vue'
import KupacProfil from '../views/KupacProfil.vue'
import ProdavacProfil from '../views/ProdavacProfil.vue'
import OceniKupca from '../views/OceniKupca.vue'
import OceniProdavca from '../views/OceniProdavca.vue'
import PrijaviKupca from '../views/PrijaviKupca.vue'
import PrijaviProdavca from '../views/PrijaviProdavca.vue'
import Ponude from '../views/Ponude.vue'
import AzuriranjeProizvoda from '../views/AzuriranjeProizvoda.vue'




const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },

  {
    path: '/login',
    name: 'login',
    component: LoginView
  },

  {
    path: '/homeAdmin',
    name: 'homeAdmin',
    component: HomeAdminView
  },

  {
    path: '/homeProdavac',
    name: 'homeProdavac',
    component: HomeProdavacView
  },

  {
    path: '/homeKupac',
    name: 'homeKupac',
    component: HomeKupacView
  },

  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },

  {
    path: '/proizvod',
    name: 'proizvod',
    component: ProizvodView 
  },

  {
    path: '/azurirajProfil',
    name: 'azurirajProfil',
    component: AzurirajProfil
  },

  {
    path: '/pregledKorisnika',
    name: 'pregledKorisnika',
    component: PregledKorisnika
  },

  {
    path: '/postaviProizvod',
    name: 'postaviProizvod',
    component: PostaviProizvod
  },

  {
    path: '/kupacProfil',
    name: 'kupacProfil',
    component: KupacProfil
  },

  {
    path: '/prodavacProfil',
    name: 'prodavacProfil',
    component: ProdavacProfil
  },

  {
    path: '/oceniKupca',
    name: 'oceniKupca',
    component: OceniKupca
  },

  {
    path: '/oceniProdavca',
    name: 'oceniProdavca',
    component: OceniProdavca
  },

  {
    path: '/prijaviKupca',
    name: 'prijaviKupca',
    component: PrijaviKupca
  },

  {
    path: '/prijaviProdavca',
    name: 'prijaviKupca',
    component: PrijaviProdavca
  },

  {
    path: '/ponude',
    name: 'ponude',
    component: Ponude
  },

  {
    path: '/azuriranjeProizvoda',
    name: 'azuriranjeProizvoda',
    component: AzuriranjeProizvoda
  }

  
 
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
