FROM node:23-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the entire application code to the container
COPY . .

# Install dependencies
RUN npm install

RUN npm run build

EXPOSE 443
ENTRYPOINT ["npm", "start", "--", "--host=0.0.0.0"]
