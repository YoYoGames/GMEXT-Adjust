

/**
 * @func adjust_init
 * @desc Initializes Adjust SDK
 * @func_end
*/
function adjust_init()

/**
 * @func adjust_track_event
 * @desc You can use the Adjust SDK to track event information by associating an event with an event token.
 * @param {string} event 
 * @param {string} json 
 * @func_end
*/
function adjust_track_event(event,json)

	
/**
 * @func adjust_add_session_callback_parameter
 * @desc Send callback parameters with each session of the Adjust SDK.
 * @param {string} key Identifiquer of the data
 * @param {string} value Value of the data
 * @func_end
*/
function adjust_add_session_callback_parameter(key,value)

/**
 * @func adjust_remove_session_callback_parameter
 * @desc Remove specific session callback parameters if they are no longer required
 * @param {string} key Key to Remove 
 * @func_end
*/
function adjust_remove_session_callback_parameter(key)

/**
 * @func adjust_reset_session_callback_parameters
 * @desc Remove all session parameters if they are no longer required.
 * @func_end
*/
function adjust_reset_session_callback_parameters()

/**
 * @func adjust_add_session_partner_parameter
 * @desc Send callback partner parameters with each session of the Adjust SDK.
 * @param {string} key identifier fo the data
 * @param {string} value value of the data
 * @func_end
*/
function adjust_add_session_partner_parameter(key,value)

/**
 * @func adjust_remove_session_partner_parameter
 * @desc Remove specific session partner parameters if they are no longer required
 * @param {string} key Key to remove
 * @func_end
*/
function adjust_remove_session_partner_parameter(key)

/**
 * @func adjust_reset_session_partner_parameters
 * @desc Remove all session partner parameters if they are no longer required.
 * @func_end
*/
function adjust_reset_session_partner_parameters()

/**
 * @module home
 * @title Adjust
 * @desc 
 * @section_func
 
 * @ref fmod_system_create
 * @ref adjust_init
 * @ref adjust_track_event
 * @ref adjust_add_session_callback_parameter
 * @ref adjust_remove_session_callback_parameter
 * @ref adjust_reset_session_callback_parameters
 * @ref adjust_add_session_partner_parameter
 * @ref adjust_remove_session_partner_parameter
 * @ref adjust_reset_session_partner_parameters

 * @section_end
 * @module_end
 */
 