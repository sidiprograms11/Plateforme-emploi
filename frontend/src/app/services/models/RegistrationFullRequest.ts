export interface RegistrationFullRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  birthDate: string;
  identityDocumentBase64: string;
  identityPhotoBase64: string;
}
