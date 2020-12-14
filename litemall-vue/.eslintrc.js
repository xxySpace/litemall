module.exports = {
  root: true,
  env: {
    node: true
  },
  // extends: ['plugin:vue/essential', '@vue/prettier'],
  // rules: {
  //   camelcase: 'off',
  //   quotes: ['error', 'single'],
  //   indent: ['error', 2, { SwitchCase: 1 }],
  //   'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
  //   'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
  // },
  parserOptions: {
    parser: 'babel-eslint'
  }
};
// module.exports = {
//   root: true,
//   parserOptions: {
//     sourceType: 'module'
//   },
//   //parser: "vue-eslint-parser",
//   env: {
//     browser: true,
//     node: true,
//     es6: true,
//   },
//   "parser": "babel-eslint",
//   parserOptions: {
//     "ecmaVersion": 7,
//     "sourceType": "module"
//   },
//   rules: {
//     'no-console': 'off'
//     //"parser": "vue-eslint-parser"
//   }
// }