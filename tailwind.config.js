/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class', // ✅ important
  content: [
    "./src/main/resources/templates/**/*.html",
    "./src/main/resources/static/js/**/*.js"
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('flowbite/plugin')
  ],
}
