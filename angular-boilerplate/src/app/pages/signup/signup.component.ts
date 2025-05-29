import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule, Router } from "@angular/router"
import { FormsModule } from "@angular/forms"
import { ToastrService } from "ngx-toastr"
import { UserService } from "../../services/user.service"

@Component({
  selector: "app-signup",
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  template: `
    <h1>Sign up</h1>

    <div class="row">
      <div class="col-md-6 offset-md-3">
        <form (ngSubmit)="handleSubmit()" #signupForm="ngForm">
          <div *ngIf="errorMessage.length > 0" class="alert alert-danger">
            <ul class="mb-0">
              <li *ngFor="let error of errorMessage">{{ error }}</li>
            </ul>
          </div>

          <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input
              type="text"
              class="form-control"
              id="name"
              name="name"
              [(ngModel)]="name"
              required
              autocomplete="off"
            >
          </div>

          <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              [(ngModel)]="email"
              required
              email
            >
          </div>

          <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input
              type="password"
              class="form-control"
              id="password"
              name="password"
              [(ngModel)]="password"
              required
            >
          </div>

          <div class="mb-3">
            <label for="passwordConfirmation" class="form-label">Confirmation</label>
            <input
              type="password"
              class="form-control"
              id="passwordConfirmation"
              name="passwordConfirmation"
              [(ngModel)]="passwordConfirmation"
              required
            >
          </div>

          <button
            type="submit"
            class="btn btn-primary"
            [disabled]="submitting"
          >
            <span *ngIf="submitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
            Create my account
          </button>
        </form>
      </div>
    </div>
    <br />
    <div class="text-center">
      <a routerLink="/account_activations/new" class="btn btn-success">
        Resend activation email
      </a>
    </div>
  `,
})
export class SignupComponent {
  name = ""
  email = ""
  password = ""
  passwordConfirmation = ""
  errorMessage: string[] = []
  submitting = false

  constructor(
    private userService: UserService,
    private router: Router,
    private toastr: ToastrService,
  ) {}

  handleSubmit(): void {
    this.submitting = true
    this.errorMessage = []

    this.userService
      .createUser({
        user: {
          name: this.name,
          email: this.email,
          password: this.password,
          password_confirmation: this.passwordConfirmation,
        },
      })
      .subscribe({
        next: (response) => {
          if (response.user) {
            if (response.flash) {
              this.toastr.success(response.flash[1])
            }
            this.router.navigate(["/"])
          }

          if (response.error) {
            this.errorMessage = response.error
          }

          this.submitting = false
        },
        error: (error) => {
          console.error(error)
          this.toastr.error("Failed to create account. Please try again.")
          this.submitting = false
        },
      })
  }
}
