const { createApp } = Vue

createApp({
  data() {
    return {
      email: '',
      password: '',
      firstName: '',
      lastName: ''
    }
  },
  methods: {
    login() {
      axios
        .post(
          '/api/login',
          { email: this.email, password: this.password },
          {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }
          }
        )
        .then((response) => {
          Swal.fire({
            position: 'middle',
            icon: 'success',
            background: '#000000c1',
            textColor: 'white',
            text: 'Your login has been successful',
            showConfirmButton: false
          }).then(() => {
            if (this.email === 'admin@admin.com') {
              window.location.href = 'web/admin.html'
            } else {
              window.location.href = 'web/manageTime.html'
            }
          })
        })
        .catch((error) => {
          this.error = error.response.data.message
          Swal.fire({
            icon: 'error',
            background: '#B22222',
            textColor: 'white',
            text: 'Verify the information you entered'
          })
        })
    }
  }
}).mount('#app')
