// These are the method of the CharybdeFS thrift client object
//
// For more info on how to use thrift with python
// look at: https://thrift.apache.org/tutorial/py
//

struct fault_descr {
  1: required bool random;        // error code must be randomized
  2: required i16 err_no;         // error code to return
  3: required i32 probability;    // 0 < probability < 100
  4: required string regexp;      // regular expression on filename
  5: required bool kill_caller;   // Must we kill the caller
  6: required i32 delay_us;       // operation delay in us
  7: required bool auto_delay;    // must auto delay like an SSD
  8: required bool one_time;      // stop injection after one fault is injected
}

service server {

    // Used to get the list of availables systems calls
    list<string> get_methods(), 

    // Used to clear all faults sources
    void clear_all_faults(),

    // Used to clear a specific method fault
    void clear_fault(1: string method),

    // Get all the faults currently injected
    map<string, fault_descr> get_faults(),

    // Set fault on a specific list of methods
    void set_fault(1: list<string> methods,    // the list of methods to operate on
                   2: bool random,             // Must we return random errno
                   3: i32 err_no,              // A specific errno to return
                   4: i32 probability,         // Fault probability over 100 000
                   5: string regexp,           // A regexp matching a victim file
                   6: bool kill_caller,        // Kill -9 the caller process
                   7: i32 delay_us,            // Delay to inject in the fs calls
                   8: bool auto_delay,         // Not implemented yet: Will be used to simulate SSDs latencies
                   9: bool one_time),          // Stop injection after one fault is injected

    // Works like set_fault but applies the fault to all methods
    void set_all_fault(1: bool random,
                       2: i32 err_no,
                       3: i32 probability,
                       4: string regexp,
                       5: bool kill_caller,
                       6: i32 delay_us,
                       7: bool auto_delay,
                       8: bool one_time),     // Stop injection after one fault is injected
}
