import { Component,  OnInit } from "@angular/core"
import { RouterOutlet } from "@angular/router"
import { CommonModule } from "@angular/common"
import { HeaderComponent } from "./layouts/header/header.component"
import { FooterComponent } from "./layouts/footer/footer.component"
import { AuthService } from "./services/auth.service"

@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterOutlet, CommonModule, HeaderComponent, FooterComponent],
  template: `
  <div class="app-container">
    <app-header></app-header>
    <main class="container mt-4 main-content">
      <router-outlet></router-outlet>
    </main>
    <app-footer></app-footer>
    </div>
  `,
  styles: [
    `
    :host {
      display: block;
      min-height: 100vh;
    }
    main {
      padding-bottom: 60px;
    }
    .app-container {
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }
    .main-content {
      flex: 1;
      padding-bottom: 2rem;
    }
  `,
  ],
})
export class AppComponent implements OnInit {
  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.checkAuthStatus()
  }
}
