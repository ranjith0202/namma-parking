import { UserResponse } from "./user-response";

export interface ApiResponse {
  status: number;
  message: string;
  data: UserResponse[];
}