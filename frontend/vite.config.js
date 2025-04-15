import { defineConfig } from "vite";
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

import fs from 'fs';

export default defineConfig({
  plugins: [react(),tailwindcss()],
  server: {
    port: 5555, // Порт для разработки (можно выбрать любой свободный порт)
    allowedHosts: ['prod-team-38-u0ku5lhd.REDACTED'], // Разрешенные хосты для сервера
    // https: {
    //   key: fs.readFileSync('ssl/privkey.pem'), // Путь к вашему ключу
    //   cert: fs.readFileSync('ssl/cert.pem'), // Путь к вашему сертификату
    // },
    // strictPort: true,
  },
});
