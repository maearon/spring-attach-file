"use client";
import type { NextPage } from 'next'
import Image from 'next/image'
import React, { useCallback, useEffect, useState } from 'react'
import Pagination from 'react-js-pagination'
import { useAppSelector } from '../../redux/hooks'
import { selectUser } from '../../redux/session/sessionSlice'
import userApi, { User } from '../../components/shared/api/userApi'
import flashMessage from '../../components/shared/flashMessages'
import { request, gql } from 'graphql-request'
import useUserApi from '../../graphql/userApi'
import { getGravatarUrl } from '@/utils/gravatar';

const Index: NextPage = () => {
  const [users, setUsers] = useState([] as User[])
  const [page, setPage] = useState(1)
  const [total_count, setTotalCount] = useState(1)
  const current_user = useAppSelector(selectUser);

  const setUsersList= useCallback(async () => { 
    userApi.index({page: page}
    ).then(response => {
      if (response.content) {
        setUsers(response.content)
        setTotalCount(response.totalElements)
      } else {
        setUsers([])
      }
    })
    .catch(error => {
      console.log(error)
    })

    // const data = useUserApi.index()
    // console.log(data)
  }, [page])

  useEffect(() => {
    setUsersList()
  }, [setUsersList])

  const handlePageChange = (pageNumber: React.SetStateAction<number>) => {
    setPage(pageNumber)
  }

  const removeUser = (index: number, userid: string) => {
    let sure = window.confirm("You sure?")
    if (sure === true) {
      userApi.destroy(userid
      ).then(response => {
          if (response.flash) {
            flashMessage(...response.flash)
            setUsersList()
          }
        })
        .catch(error => {
          console.log(error)
        })
    }
  }

  return (
    <>
    <h1>All users</h1>

    <Pagination
      activePage={page}
      itemsCountPerPage={5}
      totalItemsCount={total_count}
      pageRangeDisplayed={5}
      onChange={handlePageChange}
    />

    <ul className="users">
      {users.map((u, i) => (
      <li key={i}>
        <Image
          className={"gravatar"}
          src={getGravatarUrl(u.email, 50)}
          alt={u.name}
          width={50}
          height={50}
          priority
        />
        <a href={'/users/'+u.id}>{u.name}</a>
        {
          current_user.value.admin && String(current_user.value.id) !== String(u.id) ? (
            <>
            | <a href={'#/users/'+u.id} onClick={() => removeUser(i, u.id)}>delete</a>
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
  )
}

export default Index
