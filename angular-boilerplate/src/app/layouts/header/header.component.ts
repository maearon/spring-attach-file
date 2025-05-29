import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { Store } from "@ngrx/store"
import { Observable } from "rxjs"
import { User } from "../../models/user.model"
import { selectLoggedIn, selectUser } from "../../store/session/session.selectors"
import { AuthService } from "../../services/auth.service"

@Component({
  selector: "app-header",
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container">
        <a class="navbar-brand" routerLink="/">Sample App</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <a class="nav-link" routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Home</a>
            </li>
            <li class="nav-item" *ngIf="loggedIn$ | async">
              <a class="nav-link" routerLink="/users" routerLinkActive="active">Users</a>
            </li>
          </ul>
          <ul class="navbar-nav">
            <ng-container *ngIf="loggedIn$ | async; else loggedOut">
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                  Account
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                  <li>
                    <a class="dropdown-item" [routerLink]="['/users', (user$ | async)?.id]">Profile</a>
                  </li>
                  <li>
                    <a class="dropdown-item" [routerLink]="['/users', (user$ | async)?.id, 'edit']">Settings</a>
                  </li>
                  <li><hr class="dropdown-divider"></li>
                  <li>
                    <a class="dropdown-item" href="#" (click)="logout($event)">Log out</a>
                  </li>
                </ul>
              </li>
            </ng-container>
            <ng-template #loggedOut>
              <li class="nav-item">
                <a class="nav-link" routerLink="/login" routerLinkActive="active">Log in</a>
              </li>
            </ng-template>
          </ul>
        </div>
      </div>
    </nav>
  `,
  styles: [
    `
    .navbar-brand {
      font-weight: bold;
    }
    .dropdown-menu {
      right: 0;
      left: auto;
    }
  `,
  ],
})
export class HeaderComponent {
  loggedIn$: Observable<boolean>
  user$: Observable<User | null>

  constructor(
    private store: Store,
    private authService: AuthService,
  ) {
    this.loggedIn$ = this.store.select(selectLoggedIn)
    this.user$ = this.store.select(selectUser)
  }

  logout(event: Event): void {
    event.preventDefault()
    this.authService.logout().subscribe()
  }
}
