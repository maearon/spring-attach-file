import { Component,  OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule, ActivatedRoute } from "@angular/router"
import { NgxPaginationModule } from "ngx-pagination"
import { ToastrService } from "ngx-toastr"
import { Store } from "@ngrx/store"
import { Observable } from "rxjs"
import { User } from "../../models/user.model"
import { Micropost } from "../../models/micropost.model"
import { UserService } from "../../services/user.service"
import { MicropostService } from "../../services/micropost.service"
import { RelationshipService } from "../../services/relationship.service"
import { selectUser } from "../../store/session/session.selectors"
import { TimeAgoPipe } from "../../pipes/time-ago.pipe";

@Component({
  selector: "app-user-profile",
  standalone: true,
  imports: [CommonModule, RouterModule, NgxPaginationModule, TimeAgoPipe],
  template: `
    <div class="user-profile">
      <div *ngIf="loading" class="text-center py-4">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <div *ngIf="error" class="alert alert-danger">
        {{ error }}
      </div>

      <div *ngIf="!loading && !error" class="row">
        <div class="col-md-4">
          <div class="card mb-4">
            <div class="card-body">
              <div class="d-flex align-items-center mb-3">
                <img
                  [src]="user.gravatar"
                  [alt]="user.name"
                  class="rounded-circle me-3"
                  width="80"
                  height="80"
                >
                <h3 class="h4 mb-0">{{ user.name }}</h3>
              </div>
              <div>{{ totalCount }} micropost{{ totalCount !== 1 ? 's' : '' }}</div>
            </div>
          </div>

          <div class="card">
            <div class="card-body">
              <div class="d-flex justify-content-around">
                <a [routerLink]="['/users', user.id, 'following']" class="text-decoration-none text-center">
                  <div class="h5 mb-0">{{ user.following }}</div>
                  <div>following</div>
                </a>
                <a [routerLink]="['/users', user.id, 'followers']" class="text-decoration-none text-center">
                  <div class="h5 mb-0">{{ user.followers }}</div>
                  <div>followers</div>
                </a>
              </div>

              <div *ngIf="(currentUser$ | async)?.id !== user.id" class="text-center mt-3">
                <button
                  *ngIf="user.current_user_following_user"
                  class="btn btn-outline-danger"
                  (click)="unfollowUser()"
                  [disabled]="followLoading"
                >
                  <span *ngIf="followLoading" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Unfollow
                </button>
                <button
                  *ngIf="!user.current_user_following_user"
                  class="btn btn-primary"
                  (click)="followUser()"
                  [disabled]="followLoading"
                >
                  <span *ngIf="followLoading" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Follow
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-8">
          <h3 class="mb-4">Microposts</h3>

          <div *ngIf="microposts.length === 0" class="alert alert-info">
            No microposts found
          </div>

          <ul *ngIf="microposts.length > 0" class="list-group mb-4">
            <li *ngFor="let item of microposts | paginate: { itemsPerPage: 5, currentPage: page, totalItems: totalCount }"
                [id]="'micropost-' + item.id"
                class="list-group-item">
              <div class="d-flex">
                <img
                  [src]="item.gravatar"
                  [alt]="item.user_name"
                  class="rounded-circle me-3"
                  [width]="50"
                  [height]="50"
                >
                <div class="flex-grow-1">
                  <div class="mb-1">
                    <a [routerLink]="['/users', item.user_id]">{{ item.user_name }}</a>
                  </div>
                  <div class="mb-2">{{ item.content }}</div>
                  <img *ngIf="item.image" [src]="item.image" alt="Post image" class="img-fluid mb-2">
                  <div class="text-muted small">
                    Posted {{ item.createdAt | timeAgo }} ago.
                    <!-- <a
                      *ngIf="(currentUser$ | async)?.id === item.user_id"
                      href="#"
                      (click)="removeMicropost($event, item.id)"
                      class="ms-2"
                    >
                      delete
                    </a> -->
                  </div>
                </div>
              </div>
            </li>
          </ul>

          <div *ngIf="totalCount > 5" class="d-flex justify-content-center">
            <pagination-controls
              (pageChange)="handlePageChange($event)"
            ></pagination-controls>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class UserProfileComponent implements OnInit {
  user: any = {}
  microposts: Micropost[] = []
  loading = true
  error = ""
  page = 1
  totalCount = 0
  followLoading = false
  currentUser$: Observable<User | null>

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private micropostService: MicropostService,
    private relationshipService: RelationshipService,
    private toastr: ToastrService,
    private store: Store,
  ) {
    this.currentUser$ = this.store.select(selectUser)
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const userId = params.get("id")
      if (userId) {
        this.fetchUserProfile(userId)
      }
    })
  }

  fetchUserProfile(userId: string): void {
    // this.loading = true
    this.userService.getUser(userId, { page: this.page }).subscribe({
      next: (response) => {
        this.user = response.user
        this.microposts = response.microposts
        this.totalCount = response.total_count
        this.loading = false
        this.followLoading = false
      },
      error: (error) => {
        console.error(error)
        this.error = "Failed to load user profile"
        this.loading = false
        this.followLoading = false
      },
    })
  }

  handlePageChange(newPage: number): void {
    this.page = newPage
    this.fetchUserProfile(this.user.id)
  }

  followUser(): void {
    this.followLoading = true
    this.relationshipService.follow({ followed_id: this.user.id }).subscribe({
      next: (response) => {
        if (response.follow) {
          this.fetchUserProfile(this.user.id)
        }
        // this.followLoading = false
      },
      error: (error) => {
        console.error(error)
        this.toastr.error("Failed to follow user")
        // this.followLoading = false
      },
    })
  }

  unfollowUser(): void {
    this.followLoading = true
    this.relationshipService.unfollow(this.user.id).subscribe({
      next: (response) => {
        if (response.unfollow) {
          this.fetchUserProfile(this.user.id)
        }
        // this.followLoading = false
      },
      error: (error) => {
        console.error(error)
        this.toastr.error("Failed to unfollow user")
        // this.followLoading = false
      },
    })
  }

  removeMicropost(event: Event, micropostId: number): void {
    event.preventDefault()

    if (confirm("Are you sure?")) {
      this.micropostService.remove(micropostId).subscribe({
        next: (response) => {
          if (response.flash) {
            this.toastr.success(response.flash[1])
          } else {
            this.toastr.success("Micropost deleted successfully")
          }
          this.fetchUserProfile(this.user.id)
        },
        error: (error) => {
          console.error(error)
          this.toastr.error("Failed to delete micropost")
        },
      })
    }
  }
}
