<template>
  <tr>
    <td>{{ employee.firstname }}</td>
    <td>{{ employee.lastname }}</td>
    <td>{{ employee.position }}</td>
    <td>
      <button class="btnSeeMore" v-on:click="seeMore">See more</button>
      <button class="btnDelete" v-on:click="deleteEmployee">Delete</button>
    </td>
  </tr>
</template>

<script>
import axios from 'axios';

export default {
  name: "EmployeeComp",
  props: ["employee"],
  methods: {
    seeMore: function () {
      this.$router.push("/employee?id=" + this.employee.id);
    },
    deleteEmployee: function () {
      
      axios
        .delete("http://localhost:8081/api/employees/" + this.employee.id)

        .then((res) => {
          console.log(res);
          this.$router.go();
        })
      
        .catch((err) => {
          console.log(err);
          alert("Something went wrong!");
        });
    }
  },
};
</script>


<style>
</style>
