import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { ToastrService } from "ngx-toastr"
import { AccountActivationService } from "../../services/account-activation.service"

@Component({
  selector: "app-account-activations-new",
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h1>Resend Activation Email</h1>

    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <form (ngSubmit)="handleSubmit()" #activationForm="ngForm">
          <div *ngIf="Object.keys(errors).length > 0" class="alert alert-danger">
            <ul>
              <li *ngFor="let field of Object.keys(errors)">
                <span *ngFor="let error of errors[field]">
                  {{ field }} {{ error }}
                </span>
              </li>
            </ul>
          </div>

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
            Resend activation email
          </button>
        </form>
      </div>
    </div>
  `,
})
export class AccountActivationsNewComponent {
  email = ""
  errors: Record<string, string[]> = {}
  isSubmitting = false
  Object = Object // Make Object available in the template

  constructor(
    private router: Router,
    private toastr: ToastrService,
    private accountActivationService: AccountActivationService,
  ) {}

  handleSubmit(): void {
    this.isSubmitting = true

    this.accountActivationService
      .resendActivationEmail({
        resend_activation_email: {
          email: this.email,
        },
      })
      .subscribe({
        next: () => {
          // Clear form and errors
          this.email = ""
          this.errors = {}

          // Show success message
          this.toastr.success("The activation email has been sent again, please check your email")
          this.isSubmitting = false
        },
        error: (error) => {
          console.error(error)
          if (error.response?.data?.errors) {
            this.errors = error.response.data.errors
          } else {
            this.toastr.error("Failed to send activation email. Please try again.")
          }
          this.isSubmitting = false
        },
      })
  }
}
