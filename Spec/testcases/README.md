# Testcase 管理规则

## 当前活动版本

- 当前唯一开发版本：`V0.1.0`
- 当前唯一验收用例：`v0.1.0-testcases.xlsx`

## 版本拆分规则

- 每个 feature version 必须有独立 testcase 文件，例如 `v0.2.0-testcases.xlsx`
- 未进入当前版本范围的 testcase 不参与本轮开发验收
- 通用文件 `testcases.xlsx` 为历史导出快照，不作为当前版本准入依据

## 当前文件映射

- `v0.1.0-testcases.xlsx`：项目骨架、数据库基线、后台登录
- `v0.4.0-testcases.xlsx`：由历史 AI/多端用例快照归档出的 `V0.4.0` 初始版本文件
- `testcases.xlsx`：历史导出快照，仅保留做追溯，不作为当前版本准入依据

## 执行规则

- 顺序固定为：`roadmap -> 当前版本 testcase -> 开发 -> testcase 全绿 -> develop -> release`
- 登录页不得展示测试账号信息，该要求必须保留在 testcase 中
- `admin123 / mall123 / promotion123` 仅允许出现在 README，不允许出现在 UI
