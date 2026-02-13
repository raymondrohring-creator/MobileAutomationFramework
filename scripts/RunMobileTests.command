#!/bin/zsh

echo "========================================"
echo " Starting MobileAutomationFramework Maven Test Run"
echo "========================================"
echo ""

cd /Users/raymondrohring/appium-workspace/MobileAutomationFramework
mvn clean test

echo ""
echo "========================================"
echo " Test run complete"
echo "========================================"
echo ""
echo "Press any key to close this window..."
read -k 1
