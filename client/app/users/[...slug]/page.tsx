"use client";
import Image from "next/image";
import Link from 'next/link'
import { SetStateAction, useCallback, useEffect, useState } from 'react'
import { useAppSelector } from '../../../redux/hooks'
import { selectUser } from '../../../redux/session/sessionSlice'
import userApi, { IUserFollow, UserFollow } from '../../../components/shared/api/userApi'
import flashMessage from '../../../components/shared/flashMessages'
import Pagination from 'react-js-pagination'
import { getGravatarUrl } from "@/utils/gravatar";

const ShowFollow = ({params}: {params: {slug: string[]}}) =>{
  const [users, setUsers] = useState([] as UserFollow[])
  const [xusers, setXusers] = useState([] as UserFollow[])
  const [page, setPage] = useState(1)
  const [total_count, setTotalCount] = useState(1)
  const current_user = useAppSelector(selectUser)
  const [user, setUser] = useState({} as IUserFollow)
  const { id, follow } = params.slug.length === 2 ? { id: params.slug[0], follow: params.slug[1] } : { id: '', follow: '' };

  const setFollowPage= useCallback(async () => { 
    userApi.follow(id, page, follow as string
    ).then(response => {
      setUsers(response.users)
      setXusers(response.xusers)
      setTotalCount(response.totalElements)
      setUser(response.user)
    })
    .catch((error: any) => {
      console.log(error)
    })
  }, [page, id, follow])

  useEffect(() => {
    setFollowPage()
  }, [setFollowPage])

  const handlePageChange = (pageNumber: SetStateAction<number>) => {
    console.log(`active page is ${pageNumber}`)
    setPage(pageNumber)
  }

  const removeUser = (id: string) => {
    let sure = window.confirm("You sure?")
    if (sure === true) {
      userApi.destroy(id)
        .then(response => {
          if (response.flash) {
            flashMessage(...response.flash)
            setFollowPage()
          }
        })
        .catch(error => {
          console.log(error)
        })
    }
  }

  return (
    <>
    <div className="row">
      <aside className="col-md-4">
        <section className="user_info">
          <Image
            className={"gravatar"}
            src={getGravatarUrl(current_user.value.email, 80)}
            alt={current_user.value.name}
            width={80}
            height={80}
            priority
          />
          <h1>{current_user.value.name}</h1>
          <span><Link href={"/users/"+current_user.value.id}>view my profile</Link></span>
          <span><b>Microposts:</b> {user.micropost}</span>
        </section>

        <section className="stats">
          <div className="stats">
            <Link href={"/users/"+current_user.value.id+"/following"}>
              <strong id="following" className="stat">
                {user.following}
              </strong> following
            </Link>
            <Link href={"/users/"+current_user.value.id+"/followers"}>
              <strong id="followers" className="stat">
                {user.followers}
              </strong> followers
            </Link>
          </div>

          <div className="user_avatars">
            {xusers.length > 0 &&
            <>
            {xusers.map((u, i) => (
            <Link key={i} href={'/users/'+u.id}>
              <Image
                className={"gravatar"}
                src={getGravatarUrl(user.email, 30)}
                alt={u.name}
                width={30}
                height={30}
                priority
              />
            </Link>
            ))}
            </>
            }
          </div>
        </section>
      </aside>

      <div className="col-md-8">
        {users.length > 0 &&
        <>
        <h3>{follow?.toString()[0].toUpperCase()}{follow?.toString().slice(1)}</h3>
        <ul className="users follow">
        {users.map((u, i) => (
        <li key={i}>
          <Image
            className={"gravatar"}
            src={getGravatarUrl(u.email, 30)}
            alt={u.name}
            width={50}
            height={50}
            priority
          />
          <Link href={'/users/'+u.id}>{u.name}</Link>
          {
            current_user.value.admin && String(current_user.value.id) !== String(u.id) ? (
              <>
              | <Link href={'#/users/'+u.id} onClick={() => removeUser(u.id)}>delete</Link>
              </>
            ) : (
              <></>
            )
          }
        </li>
        ))}
        </ul>
        <Pagination
          activePage={page}
          itemsCountPerPage={5}
          totalItemsCount={total_count}
          pageRangeDisplayed={5}
          onChange={handlePageChange}
        />
        </>
        }
      </div>
    </div>
    </>
  )
}

export default  ShowFollow
