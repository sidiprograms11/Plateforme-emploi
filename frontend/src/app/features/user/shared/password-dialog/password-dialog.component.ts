import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-password-dialog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './password-dialog.html'
})
export class PasswordDialogComponent {

  password = '';
  @Output() confirm = new EventEmitter<string>();
  @Output() cancel = new EventEmitter<void>();

  submit() {
    if (this.password.trim()) {
      this.confirm.emit(this.password);
      this.password = '';
    }
  }
}