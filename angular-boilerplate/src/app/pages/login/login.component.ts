import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { FormsModule } from "@angular/forms"
import { Store } from "@ngrx/store"
import { Observable } from "rxjs"
import * as SessionActions from "../../store/session/session.actions"
import { selectLoading, selectError } from "../../store/session/session.selectors"

@Component({
  selector: "app-login",
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  template: `
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">
            <h1 class="text-center mb-0">Log in</h1>
          </div>
          <div class="card-body">
            <div *ngIf="error$ | async as error" class="alert alert-danger">
              {{ error.message || 'Login failed. Please try again.' }}
            </div>

            <form (ngSubmit)="handleSubmit()" #loginForm="ngForm">
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

              <div class="mb-3 form-check">
                <input
                  type="checkbox"
                  class="form-check-input"
                  id="rememberMe"
                  name="rememberMe"
                  [(ngModel)]="rememberMe"
                >
                <label class="form-check-label" for="rememberMe">Remember me on this computer</label>
              </div>

              <button
                type="submit"
                class="btn btn-primary w-100"
                [disabled]="loading$ | async"
              >
                <span *ngIf="loading$ | async" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                Log in
              </button>

              <div class="mt-3 text-center">
                New user? <a routerLink="/signup">Sign up now!</a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class LoginComponent {
  email = ""
  password = ""
  rememberMe = true
  loading$: Observable<boolean>
  error$: Observable<any>

  constructor(private store: Store) {
    this.loading$ = this.store.select(selectLoading)
    this.error$ = this.store.select(selectError)
  }

  handleSubmit(): void {
    this.store.dispatch(
      SessionActions.login({
        email: this.email,
        password: this.password,
        rememberMe: this.rememberMe,
      }),
    )
  }
}
