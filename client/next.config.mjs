/** @type {import('next').NextConfig} */
const nextConfig = {
  experimental: {
    reactCompiler: true,
  },
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'secure.gravatar.com',
        port: '',
        pathname: '/avatar/**',
      },
      {
        protocol: 'http',
        hostname: 'localhost:3001',
        port: '3001',
        pathname: '/**',
      },
      {
        protocol: 'https',
        hostname: 'youtube.com',
        port: '',
        pathname: '/**',
      },
      {
        protocol: 'https',
        hostname: 'spring-boilerplate.onrender.com',
        port: '',
        pathname: '/**',
      },
      {
        protocol: 'http',
        hostname: 'localhost',
        port: '8080',
        pathname: '/**',
      },
    ],
  },
  env: {  
    CLIENT_ID: process.env.CLIENT_ID,  
    REDIRECT_URI: process.env.REDIRECT_URI, 
    SCOPE: process.env.SCOPE,
    CLIENT_SECRET: process.env.CLIENT_SECRET,
    API_KEY: process.env.API_KEY
  ,}
};

export default nextConfig;
