

const { createApp } = Vue
createApp({
    data() {
      return {
        email: '',
        password: ''
      }
    },
    methods:{
        login(){
          axios.post('/api/login', `email=${this.email}&password=${this.password}`, {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
          }
          })
          .then(response => {
            Swal.fire({
              position: 'midle',
              icon: 'success',
              color: 'white',
              background: '#000000c1',
              text: 'Your login have been successful',
              showConfirmButton: false,
            })
            .then(()=> {
              if (this.email == "admin@admin.com"){
                window.location.href ='web/admin.html';
              }
              else{
                window.location.href = 'web/manageTime.html'
              }
            }
            )
          }
          )
          .catch(error => {
            this.error = error.response.data.message;
            Swal.fire({
                icon: 'error',
                color: 'white',
                background: '#B22222',
                text: 'Verifica la informacion que ingresaste',
            })
        });

        }
    }
  }).mount('#app')