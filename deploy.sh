docker build -t payment-service .
helm delete payment-service
sleep 10
cd helm
helm install payment-service payment-service/
cd ..