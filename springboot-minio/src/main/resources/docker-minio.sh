
# 拉取镜像
docker pull quay.io/minio/minio
# 创建数据储存的目录
vim /etc/default/minio
# 启动
docker run -d\
   -p 9000:9000 \
   -p 9001:9001 \
   --name minio \
   -v ~/minio/data:/data \
   -e "MINIO_ROOT_USER=ROOTNAME" \
   -e "MINIO_ROOT_PASSWORD=CHANGEME123" \
   quay.io/minio/minio server /data --console-address ":9001"