
# 拉取镜像
docker pull quay.io/minio/minio
#编辑创建配置文件
vim /etc/default/minio
# 指定账号秘密,文件挂载目录,控制台端口
MINIO_ROOT_USER=myminioadmin
MINIO_ROOT_PASSWORD=minio-secret-key-change-me
MINIO_VOLUMES="/mnt/data"
MINIO_OPTS="--console-address :9001"

# 启动
docker run -dt                                  \
  -p 9000:9000 -p 9001:9001                     \
  -v PATH:/mnt/data                             \
  -v /etc/default/minio:/etc/config.env         \
  -e "MINIO_CONFIG_ENV_FILE=/etc/config.env"    \
  --name "minio_local"                          \
  quay.io/minio/minio server --console-address ":9001"