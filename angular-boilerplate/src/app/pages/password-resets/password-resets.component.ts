import { Component,  OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { ActivatedRoute, Router } from "@angular/router"
import { ToastrService } from "ngx-toastr"
import { PasswordResetService } from "../../services/password-reset.service"

@Component({
  selector: "app-password-resets",
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h1>Reset password</h1>
    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <form (ngSubmit)="handleSubmit()" #resetForm="ngForm">
          <div *ngIf="errorMessage.length > 0" class="alert alert-danger">
            <ul>
              <li *ngFor="let error of errorMessage">
                {{ error }}
              </li>
            </ul>
          </div>

          <input type="hidden" name="email" [value]="email" />

          <div class="form-group mb-3">
            <label for="password" class="form-label">Password</label>
            <input
              class="form-control"
              type="password"
              [(ngModel)]="password"
              id="password"
              name="password"
              required
            />
          </div>

          <div class="form-group mb-3">
            <label for="passwordConfirmation" class="form-label">Confirmation</label>
            <input
              class="form-control"
              type="password"
              [(ngModel)]="passwordConfirmation"
              id="passwordConfirmation"
              name="passwordConfirmation"
              required
            />
          </div>

          <button
            type="submit"
            class="btn btn-primary"
            [disabled]="isSubmitting"
          >
            <span *ngIf="isSubmitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
            Update password
          </button>
        </form>
      </div>
    </div>
  `,
})
export class PasswordResetsComponent implements OnInit {
  resetToken = ""
  email = ""
  password = ""
  passwordConfirmation = ""
  errorMessage: string[] = []
  isSubmitting = false

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private passwordResetService: PasswordResetService,
  ) {}

  ngOnInit(): void {
    // Get reset token and email from route params
    this.resetToken = this.route.snapshot.paramMap.get("token") || ""
    this.email = this.route.snapshot.queryParamMap.get("email") || ""

    if (!this.resetToken || !this.email) {
      this.toastr.error("Invalid password reset link")
      this.router.navigate(["/password_resets/new"])
    }
  }

  handleSubmit(): void {
    this.isSubmitting = true

    this.passwordResetService
      .resetPassword(this.resetToken, {
        email: this.email,
        user: {
          password: this.password,
          password_confirmation: this.passwordConfirmation,
        },
      })
      .subscribe({
        next: (response) => {
          if (response.flash?.[0] === "danger") {
            this.toastr.error(response.flash[1])
          } else if (response.error) {
            this.errorMessage = response.error
          } else if (response.flash?.[0] === "success") {
            this.toastr.success("The password reset successfully, please try log in again")
            this.router.navigate(["/login"])
          } else {
            this.toastr.success("The password reset successfully, please try log in again")
            this.router.navigate(["/login"])
          }
          this.isSubmitting = false
        },
        error: (error) => {
          console.error(error)
          this.toastr.error("Failed to reset password. Please try again.")
          this.isSubmitting = false
        },
      })
  }
}
