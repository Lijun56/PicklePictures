/**
 * 采用传统开发方式，针对每个请求都要单独编写代码
 * OpenAPI: auto generate functions for each backend request api
 * how to : modify package.json and use node to run this file to generate apis files
 */
import { generateService } from '@umijs/openapi'

generateService({
  requestLibPath: "import request from '@/request'",
  schemaPath: 'http://localhost:8123/api/v2/api-docs',
  serversPath: './src',
})
