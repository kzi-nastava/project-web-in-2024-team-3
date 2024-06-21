import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import HomeAdminView from '../views/AdminHome.vue'
import HomeProdavacView from '../views/ProdavacHome.vue'
import HomeKupacView from '../views/KupacHome.vue'
import RegisterView from '../views/RegisterView.vue'



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
  }

  
 
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
