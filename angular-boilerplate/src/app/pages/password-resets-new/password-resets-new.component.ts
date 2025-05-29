import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { ToastrService } from "ngx-toastr"
import { PasswordResetService } from "../../services/password-reset.service"

@Component({
  selector: "app-password-resets-new",
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h1>Forgot password</h1>

    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <form (ngSubmit)="handleSubmit()" #resetForm="ngForm">
          <div class="form-group mb-3">
            <label for="email" class="form-label">Email</label>
            <input
              class="form-control"
              type="email"
              [(ngModel)]="email"
              id="email"
              name="email"
              required
            />
          </div>

          <button
            type="submit"
            class="btn btn-primary"
            [disabled]="isSubmitting"
          >
            <span *ngIf="isSubmitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
            Submit
          </button>
        </form>
      </div>
    </div>
  `,
})
export class PasswordResetsNewComponent {
  email = ""
  isSubmitting = false

  constructor(
    private router: Router,
    private toastr: ToastrService,
    private passwordResetService: PasswordResetService,
  ) {}

  handleSubmit(): void {
    this.isSubmitting = true

    this.passwordResetService
      .requestPasswordReset({
        password_reset: {
          email: this.email,
        },
      })
      .subscribe({
        next: () => {
          this.toastr.success("The password reset email has been sent, please check your email")
          this.email = ""
          this.isSubmitting = false
        },
        error: (error) => {
          console.error(error)
          this.toastr.error("Failed to send password reset email. Please try again.")
          this.isSubmitting = false
        },
      })
  }
}
