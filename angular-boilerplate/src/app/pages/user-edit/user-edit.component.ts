import { Component,  OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { ActivatedRoute, Router } from "@angular/router"
import { ToastrService } from "ngx-toastr"
import { UserService } from "../../services/user.service"

@Component({
  selector: "app-user-edit",
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container py-4">
      <h1 class="mb-4">Update your profile</h1>

      <div *ngIf="loading" class="text-center py-4">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <div *ngIf="!loading" class="row">
        <div class="col-md-4">
          <div class="card mb-4">
            <div class="card-body text-center">
              <img
                [src]="gravatar"
                [alt]="user.name"
                class="rounded-circle mb-3"
                width="80"
                height="80"
              >
              <h3 class="h5">{{ user.name }}</h3>
              <p class="mt-2">
                <a href="https://gravatar.com/emails" target="_blank" rel="noopener noreferrer">change</a>
              </p>
            </div>
          </div>
        </div>

        <div class="col-md-8">
          <div class="card">
            <div class="card-body">
              <form (ngSubmit)="handleSubmit()" #editForm="ngForm">
                <div *ngIf="errors.length" class="alert alert-danger mb-4">
                  <ul class="mb-0">
                    <li *ngFor="let error of errors">{{ error }}</li>
                  </ul>
                </div>

                <div class="mb-3">
                  <label for="name" class="form-label">Name</label>
                  <input
                    type="text"
                    class="form-control"
                    id="name"
                    name="name"
                    [(ngModel)]="formData.name"
                    required
                  >
                </div>

                <div class="mb-3">
                  <label for="email" class="form-label">Email</label>
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    name="email"
                    [(ngModel)]="formData.email"
                    required
                  >
                </div>

                <div class="mb-3">
                  <label for="password" class="form-label">Password</label>
                  <input
                    type="password"
                    class="form-control"
                    id="password"
                    name="password"
                    [(ngModel)]="formData.password"
                  >
                </div>

                <div class="mb-4">
                  <label for="passwordConfirmation" class="form-label">Confirm Password</label>
                  <input
                    type="password"
                    class="form-control"
                    id="passwordConfirmation"
                    name="passwordConfirmation"
                    [(ngModel)]="formData.passwordConfirmation"
                  >
                </div>

                <button
                  type="submit"
                  class="btn btn-primary"
                  [disabled]="submitting"
                >
                  <span *ngIf="submitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Save changes
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class UserEditComponent implements OnInit {
  userId = ""
  user: any = {
    name: "",
    email: "",
  }
  gravatar = ""
  loading = true
  submitting = false
  errors: string[] = []

  formData = {
    name: "",
    email: "",
    password: "",
    passwordConfirmation: "",
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService,
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.userId = params.get("id") || ""
      if (this.userId) {
        this.fetchUserData()
      }
    })
  }

  fetchUserData(): void {
    this.loading = true
    this.userService.editUser(this.userId).subscribe({
      next: (data) => {
        this.user = data.user
        this.gravatar = data.gravatar
        this.formData.name = data.user.name
        this.formData.email = data.user.email
        this.loading = false
      },
      error: (error) => {
        console.error(error)
        this.toastr.error("Failed to fetch user data")
        this.router.navigate(["/"])
        this.loading = false
      },
    })
  }

  handleSubmit(): void {
    this.submitting = true
    this.errors = []

    this.userService
      .updateUser(this.userId, {
        user: {
          name: this.formData.name,
          email: this.formData.email,
          password: this.formData.password,
          password_confirmation: this.formData.passwordConfirmation,
        },
      })
      .subscribe({
        next: (response) => {
          if (response.flash_success) {
            this.toastr.success(response.flash_success[1])
            this.router.navigate([`/users/${this.userId}`])
          }

          if (response.error) {
            this.errors = Array.isArray(response.error) ? response.error : [response.error]
          }

          this.submitting = false
        },
        error: (error) => {
          console.error(error)
          if (error.error?.errors) {
            const errorData = error.error.errors
            const errorMessages: string[] = []
            Object.keys(errorData).forEach((key) => {
              errorData[key].forEach((message: string) => {
                errorMessages.push(`${key} ${message}`)
              })
            })
            this.errors = errorMessages
          } else {
            this.toastr.error("Failed to update profile")
          }
          this.submitting = false
        },
      })
  }
}
