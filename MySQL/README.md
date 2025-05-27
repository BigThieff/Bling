## 1. 用户服务数据库 `bling_user`

**数据库作用**  
存储系统的用户基本信息。

**主要表及字段说明**

| 表名         | 字段名           | 说明             |
| ---------- | ------------- | -------------- |
| `sys_user` | `id`          | 用户唯一主键，自增ID    |
|            | `nickname`    | 用户昵称           |
|            | `phone`       | 手机号码           |
|            | `email`       | 电子邮箱           |
|            | `avatar`      | 用户头像URL        |
|            | `status`      | 用户状态，0=禁用，1=启用 |
|            | `create_time` | 记录创建时间         |

**作用说明**

- `sys_user` 表保存的是用户的基本信息，是用户身份的核心数据。

- `status` 字段控制用户是否启用，比如禁用后用户无法登录。

---

## 2. 认证服务数据库 `bling_auth`

**数据库作用**  
管理用户的认证信息，支持多种认证方式（密码、第三方授权等）。

**主要表及字段说明**

| 表名              | 字段名             | 说明                                         |
| --------------- | --------------- | ------------------------------------------ |
| `sys_user_auth` | `id`            | 认证信息主键，自增ID                                |
|                 | `user_id`       | 关联 `sys_user.id`，对应用户                      |
|                 | `identity_type` | 认证类型，如 password（密码），github（第三方），phone（手机号） |
|                 | `identifier`    | 认证标识，如用户名、手机号、第三方ID                        |
|                 | `credential`    | 认证凭证，如密码或token（一般加密存储）                     |

**作用说明**

- 支持多种登录方式，不同方式通过 `identity_type` 区分。

- `identifier` 唯一标识用户身份，`credential` 用于校验。

- `user_id` 关联到用户表，表示认证信息属于哪个用户。

---

## 3. 权限服务数据库 `bling_permission`

**数据库作用**  
管理系统角色与权限，用于控制用户能访问和操作哪些资源。

**主要表及字段说明**

| 表名                    | 字段名             | 说明                            |
| --------------------- | --------------- | ----------------------------- |
| `sys_role`            | `id`            | 角色ID，自增主键                     |
|                       | `name`          | 角色名称（如管理员、普通用户）               |
|                       | `code`          | 角色唯一代码（如 admin、user）          |
|                       | `description`   | 角色描述                          |
|                       | `create_time`   | 创建时间                          |
| `sys_permission`      | `id`            | 权限ID，自增主键                     |
|                       | `name`          | 权限名称（如“查看用户”、“编辑用户”）          |
|                       | `code`          | 权限唯一代码（如 user:view、user:edit） |
|                       | `type`          | 权限类型（如 menu、button、api）       |
|                       | `url`           | 权限对应的接口URL（如 /api/users）      |
|                       | `method`        | HTTP请求方法（GET、POST等）           |
|                       | `parent_id`     | 父权限ID，支持权限树结构                 |
|                       | `create_time`   | 创建时间                          |
| `sys_user_role`       | `user_id`       | 用户ID，关联 `sys_user.id`         |
|                       | `role_id`       | 角色ID，关联 `sys_role.id`         |
| `sys_role_permission` | `role_id`       | 角色ID，关联 `sys_role.id`         |
|                       | `permission_id` | 权限ID，关联 `sys_permission.id`   |

**作用说明**

- `sys_role` 表定义系统中可用的角色，例如“管理员”、“普通用户”。

- `sys_permission` 表定义所有可分配的权限点，如某个接口的访问权限。

- `sys_user_role` 关联用户和角色，一个用户可以有多个角色。

- `sys_role_permission` 关联角色和权限，角色拥有多个权限。

- 通过角色和权限的关联，实现基于角色的访问控制（RBAC）。

---

## 角色说明举例

- **管理员（admin）**
  
  - 拥有系统中所有权限，如查看用户列表、编辑用户、管理权限等。
  
  - 该角色在 `sys_role` 表中 `code` 字段为 `admin`。
  
  - 通过 `sys_role_permission` 关联所有关键权限。

- **普通用户（user）**
  
  - 权限受限，仅能访问或操作自己的数据，或只能查看有限信息。
  
  - `sys_role` 中 `code` 为 `user`。
  
  - 权限只包含查看类权限。

---

## 数据库之间的关系总结

- 用户表 `sys_user` 是系统用户身份的核心，其他表通过 `user_id` 关联到它。

- 认证表 `sys_user_auth` 负责登录认证，支持多种登录方式。

- 权限表 `sys_role` 和 `sys_permission` 定义了角色与权限模型，实现用户权限控制。

- `sys_user_role` 和 `sys_role_permission` 是多对多关联表，连接用户-角色和角色-权限。













## 一、数据库设计说明

### 1. 用户服务（bling_user）

用于管理用户的基本信息。

#### 表：`sys_user`

| 字段名           | 类型           | 说明             |
| ------------- | ------------ | -------------- |
| `id`          | BIGINT       | 用户唯一标识，自增主键    |
| `nickname`    | VARCHAR(50)  | 用户昵称           |
| `phone`       | VARCHAR(20)  | 手机号            |
| `email`       | VARCHAR(100) | 邮箱             |
| `avatar`      | VARCHAR(255) | 头像 URL         |
| `status`      | TINYINT      | 用户状态：0=禁用，1=启用 |
| `create_time` | TIMESTAMP    | 创建时间，默认当前时间    |

---

### 2. 认证服务（bling_auth）

用于管理用户的登录认证信息。

#### 表：`sys_user_auth`

| 字段名             | 类型           | 说明                             |
| --------------- | ------------ | ------------------------------ |
| `id`            | BIGINT       | 主键，自增                          |
| `user_id`       | BIGINT       | 关联 `sys_user.id`，表示该认证属于哪个用户   |
| `identity_type` | VARCHAR(50)  | 认证方式：如 password、github、phone 等 |
| `identifier`    | VARCHAR(100) | 认证标识：用户名、手机号、第三方 ID 等          |
| `credential`    | VARCHAR(255) | 密码或 token（已加密）                 |

> ⚠️ 注意：identity_type + identifier 设置为唯一键，确保一个用户标识只有一种认证方式一次性存在。

---

### 3. 权限服务（bling_permission）

用于管理角色、权限和分配关系，支持 RBAC（基于角色的访问控制）。

#### 表：`sys_role`

| 字段名           | 类型           | 说明                     |
| ------------- | ------------ | ---------------------- |
| `id`          | BIGINT       | 主键                     |
| `name`        | VARCHAR(50)  | 角色名称，如“管理员”、“普通用户”     |
| `code`        | VARCHAR(50)  | 唯一标识符，如 `ADMIN`、`USER` |
| `description` | VARCHAR(255) | 描述                     |
| `create_time` | TIMESTAMP    | 创建时间                   |

> 示例角色：

- `ADMIN`：管理员，拥有所有权限

- `USER`：普通用户，权限受限

---

#### 表：`sys_permission`

| 字段名           | 类型           | 说明                                 |
| ------------- | ------------ | ---------------------------------- |
| `id`          | BIGINT       | 主键                                 |
| `name`        | VARCHAR(100) | 权限名称，如“添加用户”、“删除菜单”                |
| `code`        | VARCHAR(100) | 唯一权限标识，如 `user:add`, `menu:delete` |
| `type`        | VARCHAR(20)  | 类型：`menu` 菜单，`button` 按钮，`api` 接口  |
| `url`         | VARCHAR(255) | 对应接口路径或资源地址                        |
| `method`      | VARCHAR(10)  | HTTP 方法：GET、POST、PUT、DELETE 等      |
| `parent_id`   | BIGINT       | 上级权限 ID（用于构建权限树）                   |
| `create_time` | TIMESTAMP    | 创建时间                               |

---

#### 表：`sys_user_role`

| 字段名       | 类型     | 说明                     |
| --------- | ------ | ---------------------- |
| `user_id` | BIGINT | 用户 ID，关联 `sys_user.id` |
| `role_id` | BIGINT | 角色 ID，关联 `sys_role.id` |

> 多对多关系表：一个用户可以有多个角色。

---

#### 表：`sys_role_permission`

| 字段名             | 类型     | 说明                           |
| --------------- | ------ | ---------------------------- |
| `role_id`       | BIGINT | 角色 ID，关联 `sys_role.id`       |
| `permission_id` | BIGINT | 权限 ID，关联 `sys_permission.id` |

> 多对多关系表：一个角色可以有多个权限。

---

## 二、权限使用说明（RBAC）

1. 用户通过认证（auth）模块登录，系统根据其身份信息（`identity_type + identifier`）查找对应的用户。

2. 根据用户 ID 查询 `sys_user_role` 获取用户所有角色。

3. 再根据角色 ID 查询 `sys_role_permission` 获取所有权限 ID。

4. 最后查询 `sys_permission` 获取接口 URL、Method，并判断是否允许访问。

---

## 三、管理员相关说明

你可以通过手动插入以下数据来初始化一名管理员：

### 示例 SQL 初始化（管理员用户）：

sql

复制编辑

`-- 插入用户 INSERT INTO bling_user.sys_user (nickname, phone, email, avatar, status) VALUES ('管理员', '13800000000', 'admin@example.com', NULL, 1);  -- 获取上面插入的 ID，比如为 1 -- 插入认证信息 INSERT INTO bling_auth.sys_user_auth (user_id, identity_type, identifier, credential) VALUES (1, 'password', 'admin', '$2a$10$xxxx'); -- 密码加密建议使用 bcrypt  -- 插入管理员角色 INSERT INTO bling_permission.sys_role (name, code, description) VALUES ('管理员', 'ADMIN', '系统管理员');  -- 获取角色 ID，比如为 1 -- 绑定角色 INSERT INTO bling_permission.sys_user_role (user_id, role_id) VALUES (1, 1);`
