
export default {
  bootstrap: () => import('./main.server.mjs').then(m => m.default),
  inlineCriticalCss: true,
  baseHref: '/',
  locale: undefined,
  routes: [
  {
    "renderMode": 2,
    "preload": [
      "chunk-RYWSOM4R.js",
      "chunk-XEH2DIWL.js",
      "chunk-JRTDSWL7.js",
      "chunk-WYIYOJMX.js"
    ],
    "route": "/"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-6AOVRYUW.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/login"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-EVSNSD2R.js",
      "chunk-JRTDSWL7.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/signup"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-YED7CTH4.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-CICQBSNT.js",
      "chunk-XEH2DIWL.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/1"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-QMNUOHLB.js",
      "chunk-JRTDSWL7.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/1/edit"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/1/following"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/1/followers"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-CICQBSNT.js",
      "chunk-XEH2DIWL.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/2"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-QMNUOHLB.js",
      "chunk-JRTDSWL7.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/2/edit"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/2/following"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/2/followers"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-CICQBSNT.js",
      "chunk-XEH2DIWL.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/3"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-QMNUOHLB.js",
      "chunk-JRTDSWL7.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/3/edit"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/3/following"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/3/followers"
  },
  {
    "renderMode": 0,
    "preload": [
      "chunk-CICQBSNT.js",
      "chunk-XEH2DIWL.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/*"
  },
  {
    "renderMode": 0,
    "preload": [
      "chunk-QMNUOHLB.js",
      "chunk-JRTDSWL7.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/*/edit"
  },
  {
    "renderMode": 0,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/*/following"
  },
  {
    "renderMode": 0,
    "preload": [
      "chunk-D57LLZPC.js",
      "chunk-WYIYOJMX.js",
      "chunk-AXGAXXZL.js"
    ],
    "route": "/users/*/followers"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-TDNEYZ26.js"
    ],
    "route": "/about"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-ROJN6LSS.js"
    ],
    "route": "/contact"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-2SVYBXX3.js",
      "chunk-R43664O5.js"
    ],
    "route": "/account_activations/token1/edit"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-2SVYBXX3.js",
      "chunk-R43664O5.js"
    ],
    "route": "/account_activations/token2/edit"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-2SVYBXX3.js",
      "chunk-R43664O5.js"
    ],
    "route": "/account_activations/token3/edit"
  },
  {
    "renderMode": 0,
    "preload": [
      "chunk-2SVYBXX3.js",
      "chunk-R43664O5.js"
    ],
    "route": "/account_activations/*/edit"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-3IW44OCK.js",
      "chunk-R43664O5.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/account_activations/new"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-YMFTKDFU.js",
      "chunk-CCXWQ3M3.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/password_resets/new"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-7ERG6DGI.js",
      "chunk-CCXWQ3M3.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/password_resets/token1"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-7ERG6DGI.js",
      "chunk-CCXWQ3M3.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/password_resets/token2"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-7ERG6DGI.js",
      "chunk-CCXWQ3M3.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/password_resets/token3"
  },
  {
    "renderMode": 0,
    "preload": [
      "chunk-7ERG6DGI.js",
      "chunk-CCXWQ3M3.js",
      "chunk-JRTDSWL7.js"
    ],
    "route": "/password_resets/*"
  },
  {
    "renderMode": 2,
    "preload": [
      "chunk-VGZREPXA.js"
    ],
    "route": "/**"
  }
],
  entryPointToBrowserMapping: undefined,
  assets: {
    'index.csr.html': {size: 31544, hash: '08bc616810cc8330da3b0a6d05b5fa2c13ccee9f815f9ff785eafa35eb4fd43f', text: () => import('./assets-chunks/index_csr_html.mjs').then(m => m.default)},
    'index.server.html': {size: 32057, hash: '487228b1fc21f7f6b1d886816ed4b89f0e21aadf0e1e03f29107718130a1a665', text: () => import('./assets-chunks/index_server_html.mjs').then(m => m.default)},
    'login/index.html': {size: 35331, hash: '5367fd46ee2bf5bcf4d568e435c9dd318f37e0509d9d0ff2daec5d8278227264', text: () => import('./assets-chunks/login_index_html.mjs').then(m => m.default)},
    'signup/index.html': {size: 35530, hash: '8e755265dddcc95061ac39a85091f9c19b8ff2ea21554d8d83d848b00184b73a', text: () => import('./assets-chunks/signup_index_html.mjs').then(m => m.default)},
    'users/1/index.html': {size: 34464, hash: '96fe4969a200521adf3e736402344635839cf2416c9f1c59c622bfd4cab15806', text: () => import('./assets-chunks/users_1_index_html.mjs').then(m => m.default)},
    'index.html': {size: 37517, hash: '997c648ac83a85ee9be1a7218c8a40e48b2c19d787c0552667931cafb1444b4a', text: () => import('./assets-chunks/index_html.mjs').then(m => m.default)},
    'users/1/edit/index.html': {size: 35889, hash: '9e4a55888114712e5a617b88a7956e965bd13cf8cdd35241738fcef5cbc8fab5', text: () => import('./assets-chunks/users_1_edit_index_html.mjs').then(m => m.default)},
    'users/2/index.html': {size: 34464, hash: '96fe4969a200521adf3e736402344635839cf2416c9f1c59c622bfd4cab15806', text: () => import('./assets-chunks/users_2_index_html.mjs').then(m => m.default)},
    'users/1/followers/index.html': {size: 35550, hash: '2a2080910a4965ffc1e53713977778d44eefe715d8e2626b1751b7d1240d33b0', text: () => import('./assets-chunks/users_1_followers_index_html.mjs').then(m => m.default)},
    'users/2/edit/index.html': {size: 35889, hash: '9e4a55888114712e5a617b88a7956e965bd13cf8cdd35241738fcef5cbc8fab5', text: () => import('./assets-chunks/users_2_edit_index_html.mjs').then(m => m.default)},
    'users/2/following/index.html': {size: 35550, hash: '0582fb2e3751f97ba627f65a336828c696c082e218736823387995187b9f52ce', text: () => import('./assets-chunks/users_2_following_index_html.mjs').then(m => m.default)},
    'users/2/followers/index.html': {size: 35550, hash: 'a7b513d502717e7eaffead1659a8f23443f1d446ad30b249732c7f8fc28dfe4e', text: () => import('./assets-chunks/users_2_followers_index_html.mjs').then(m => m.default)},
    'users/3/index.html': {size: 34464, hash: '96fe4969a200521adf3e736402344635839cf2416c9f1c59c622bfd4cab15806', text: () => import('./assets-chunks/users_3_index_html.mjs').then(m => m.default)},
    'users/3/edit/index.html': {size: 35889, hash: '9e4a55888114712e5a617b88a7956e965bd13cf8cdd35241738fcef5cbc8fab5', text: () => import('./assets-chunks/users_3_edit_index_html.mjs').then(m => m.default)},
    'users/3/following/index.html': {size: 35550, hash: '4912a342999492e81a5ca2cb57980120769de73e372319af86d5f601d89ac8b0', text: () => import('./assets-chunks/users_3_following_index_html.mjs').then(m => m.default)},
    'users/3/followers/index.html': {size: 35550, hash: '1ec47b050f62bf1b573bd14e036ab2f1e48a290c37062a613f8f3266e494c197', text: () => import('./assets-chunks/users_3_followers_index_html.mjs').then(m => m.default)},
    'about/index.html': {size: 34588, hash: 'ec75db3551d6247f86d8563cef59a524611530aa935f9364bdba85c811a0afe7', text: () => import('./assets-chunks/about_index_html.mjs').then(m => m.default)},
    'contact/index.html': {size: 34317, hash: 'c6e9e99142fd92b482d7b8c9d49be2b936419fad9b88b14eca109dd48efc7436', text: () => import('./assets-chunks/contact_index_html.mjs').then(m => m.default)},
    'account_activations/token1/edit/index.html': {size: 37933, hash: 'd7ca842bc30dd1aec1e32d8e71d116564b24c067dd74d2cca63e5e1cd4832f51', text: () => import('./assets-chunks/account_activations_token1_edit_index_html.mjs').then(m => m.default)},
    'users/index.html': {size: 34946, hash: 'f673fdae078c20773bd696a934b9788f415dfee7d6294a968a1d461526253dcb', text: () => import('./assets-chunks/users_index_html.mjs').then(m => m.default)},
    'account_activations/token2/edit/index.html': {size: 37933, hash: 'd7ca842bc30dd1aec1e32d8e71d116564b24c067dd74d2cca63e5e1cd4832f51', text: () => import('./assets-chunks/account_activations_token2_edit_index_html.mjs').then(m => m.default)},
    'account_activations/token3/edit/index.html': {size: 37933, hash: 'd7ca842bc30dd1aec1e32d8e71d116564b24c067dd74d2cca63e5e1cd4832f51', text: () => import('./assets-chunks/account_activations_token3_edit_index_html.mjs').then(m => m.default)},
    'users/1/following/index.html': {size: 35550, hash: 'bd5fbda96bb53575be3f4195cb551a69665634a046ee0cccc424f282116b2f7e', text: () => import('./assets-chunks/users_1_following_index_html.mjs').then(m => m.default)},
    'account_activations/new/index.html': {size: 34777, hash: '638e65080f74c040d7570b2e04e4ab8ddfd55fe00c3d7e674763203718a82a53', text: () => import('./assets-chunks/account_activations_new_index_html.mjs').then(m => m.default)},
    'password_resets/new/index.html': {size: 34737, hash: 'b9e8ae71592c101b7cbbefb5c4149911ed92bdc4f5338f390cc2b36fce648cf9', text: () => import('./assets-chunks/password_resets_new_index_html.mjs').then(m => m.default)},
    'password_resets/token1/index.html': {size: 35231, hash: 'af49fff49a0c400bb5929efc650e598d188eb95b7fc92d13d51c0376d7036a74', text: () => import('./assets-chunks/password_resets_token1_index_html.mjs').then(m => m.default)},
    'password_resets/token2/index.html': {size: 35231, hash: 'af49fff49a0c400bb5929efc650e598d188eb95b7fc92d13d51c0376d7036a74', text: () => import('./assets-chunks/password_resets_token2_index_html.mjs').then(m => m.default)},
    'password_resets/token3/index.html': {size: 35231, hash: 'af49fff49a0c400bb5929efc650e598d188eb95b7fc92d13d51c0376d7036a74', text: () => import('./assets-chunks/password_resets_token3_index_html.mjs').then(m => m.default)},
    'styles-5INURTSO.css': {size: 0, hash: 'menYUTfbRu8', text: () => import('./assets-chunks/styles-5INURTSO_css.mjs').then(m => m.default)}
  },
};
