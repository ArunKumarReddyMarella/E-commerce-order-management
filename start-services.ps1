 # E-commerce Microservices Startup Script
# This script starts all services in the correct order

Write-Host "Starting E-commerce Microservices..." -ForegroundColor Green

# Function to start a service
function Start-Service {
    param(
        [string]$ServiceName,
        [string]$ServicePath
    )
    
    Write-Host "Starting $ServiceName..." -ForegroundColor Yellow
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$ServicePath'; mvn spring-boot:run" -WindowStyle Normal
    Start-Sleep -Seconds 10  # Wait for service to start
}

# Start Config Server first
Write-Host "Starting Config Server..." -ForegroundColor Cyan
Start-Service "Config Server" "config-server"

# Wait for config server to be ready
Write-Host "Waiting for Config Server to be ready..." -ForegroundColor Cyan
Start-Sleep -Seconds 15

# Start Eureka Server
Write-Host "Starting Eureka Server..." -ForegroundColor Cyan
Start-Service "Eureka Server" "eureka-server"

# Wait for eureka to be ready
Start-Sleep -Seconds 10

# Start all microservices
Write-Host "Starting Microservices..." -ForegroundColor Cyan

Start-Service "Product Service" "product-service"
Start-Service "Order Service" "order-service"
Start-Service "User Service" "user-service"
Start-Service "Payment Service" "payment-service"
Start-Service "Notification Service" "notification-service"

# Start API Gateway last
Start-Sleep -Seconds 5
Write-Host "Starting API Gateway..." -ForegroundColor Cyan
Start-Service "API Gateway" "api-gateway"

Write-Host "All services started!" -ForegroundColor Green
Write-Host "Config Server: http://localhost:8888" -ForegroundColor White
Write-Host "Eureka Server: http://localhost:8761" -ForegroundColor White
Write-Host "API Gateway: http://localhost:8080" -ForegroundColor White
Write-Host "Product Service: http://localhost:8081" -ForegroundColor White
Write-Host "Order Service: http://localhost:8083" -ForegroundColor White
Write-Host "User Service: http://localhost:8084" -ForegroundColor White
Write-Host "Payment Service: http://localhost:8085" -ForegroundColor White
Write-Host "Notification Service: http://localhost:8086" -ForegroundColor White 