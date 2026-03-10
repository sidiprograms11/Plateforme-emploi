import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  model = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    birthDate: ''
  };

  identityFile!: File;
  photoFile!: File;

  loading = false;

  constructor(private http: HttpClient) {}

  onIdentitySelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.identityFile = input.files[0];
    }
  }

  onPhotoSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.photoFile = input.files[0];
    }
  }

  submit() {
    if (!this.identityFile || !this.photoFile) {
      alert("Veuillez ajouter les deux images");
      return;
    }

    this.loading = true;

    const formData = new FormData();

    formData.append('email', this.model.email);
    formData.append('password', this.model.password);
    formData.append('firstName', this.model.firstName);
    formData.append('lastName', this.model.lastName);
    formData.append('birthDate', this.model.birthDate);

    formData.append('identityDocument', this.identityFile);
    formData.append('identityPhoto', this.photoFile);

    this.http.post(
      'http://localhost:8080/api/auth/register',
      formData
    ).subscribe({
      next: () => {
        alert('✅ Inscription envoyée');
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        alert('❌ Erreur inscription');
        this.loading = false;
      }
    });
  }
}