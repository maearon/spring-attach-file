import { Component,  OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule, ActivatedRoute,  Router } from "@angular/router"
import { NgxPaginationModule } from "ngx-pagination"
import { ToastrService } from "ngx-toastr"
import { UserService } from "../../services/user.service"
import { User } from "../../models/user.model"

@Component({
  selector: "app-show-follow",
  standalone: true,
  imports: [CommonModule, RouterModule, NgxPaginationModule],
  template: `
    <div class="container py-4">
      <!-- User info -->
      <div class="text-center mb-4">
        <img
          [src]="'https://secure.gravatar.com/avatar/' + profileUser.gravatar + '?s=100'"
          [alt]="profileUser.name"
          class="rounded-circle mb-3"
          width="100"
          height="100"
        >
        <h1 class="h4">{{ profileUser.name }}</h1>
        <div>{{ profileUser.micropost }} micropost{{ profileUser.micropost !== 1 ? 's' : '' }}</div>
      </div>

      <!-- Tabs -->
      <ul class="nav nav-tabs justify-content-center mb-4">
        <li class="nav-item">
          <a class="nav-link" [routerLink]="['/users', userId]" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Profile</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" [routerLink]="['/users', userId, 'following']" routerLinkActive="active">Following</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" [routerLink]="['/users', userId, 'followers']" routerLinkActive="active">Followers</a>
        </li>
      </ul>

      <!-- Content -->
      <div *ngIf="loading" class="text-center py-4">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <div *ngIf="!loading">
        <h2 class="h5 mb-4">{{ type === 'following' ? 'Following' : 'Followers' }} ({{ totalCount }})</h2>

        <div *ngIf="users.length === 0" class="text-center py-4">
          <p>No {{ type === 'following' ? 'following' : 'followers' }} yet.</p>
        </div>

        <ul *ngIf="users.length > 0" class="list-group mb-4">
          <li *ngFor="let followUser of users | paginate: { itemsPerPage: 10, currentPage: page, totalItems: totalCount }"
              class="list-group-item d-flex align-items-center">
            <img
              [src]="followUser.gravatar"
              [alt]="followUser.name"
              class="rounded-circle me-3"
              width="50"
              height="50"
            >
            <a [routerLink]="['/users', followUser.id]" class="flex-grow-1">{{ followUser.name }}</a>
          </li>
        </ul>

        <div *ngIf="totalCount > 10" class="d-flex justify-content-center">
          <pagination-controls
            (pageChange)="handlePageChange($event)"
          ></pagination-controls>
        </div>
      </div>
    </div>
  `,
})
export class ShowFollowComponent implements OnInit {
  userId = ""
  type = "following"
  loading = true
  page = 1
  users: User[] = []
  totalCount = 0
  profileUser: any = {
    name: "",
    gravatar: "",
    micropost: 0,
    following: 0,
    followers: 0,
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
    })

    this.route.data.subscribe((data) => {
      this.type = data["type"] || "following"
      this.fetchData()
    })
  }

  fetchData(): void {
    this.loading = true

    if (this.type === "following") {
      this.userService.getFollowing(this.userId, this.page).subscribe({
        next: this.handleResponse.bind(this),
        error: this.handleError.bind(this),
      })
    } else {
      this.userService.getFollowers(this.userId, this.page).subscribe({
        next: this.handleResponse.bind(this),
        error: this.handleError.bind(this),
      })
    }
  }

  handleResponse(response: any): void {
    this.users = response.users
    this.totalCount = response.totalElements
    this.profileUser = response.user
    this.loading = false
  }

  handleError(error: any): void {
    console.error(error)
    this.toastr.error(`Failed to fetch ${this.type}`)
    this.loading = false
  }

  handlePageChange(newPage: number): void {
    this.page = newPage
    this.fetchData()
  }
}
